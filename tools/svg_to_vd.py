"""Convert official Lucide SVGs to Android VectorDrawables, preserving strokes.

Lucide icons are stroke-based: fill=none, stroke=currentColor, width 2,
round caps/joins. Generic converters drop stroke attrs, making invisible
icons. This maps path/circle/rect/line/polyline/polygon to <path> entries
with android:strokeColor so tint works at runtime.

Usage: python tools/svg_to_vd.py tmp_assets/icons app/src/main/res/drawable
"""
import os
import re
import sys
import xml.etree.ElementTree as ET

# Spec 7.6: these render filled (fill = current color).
FILLED = {"play", "pause", "skip-back", "skip-forward", "circle-check"}


def num(v):
    if v == int(v):
        return str(int(v))
    return "%g" % v


def shape_to_path(tag, a):
    if tag == "path":
        return a["d"]
    if tag == "circle":
        cx, cy, r = float(a["cx"]), float(a["cy"]), float(a["r"])
        c = 0.552284749831 * r
        return (
            f"M{num(cx - r)},{num(cy)}"
            f"C{num(cx - r)},{num(cy - c)} {num(cx - c)},{num(cy - r)}"
            f" {num(cx)},{num(cy - r)}"
            f"C{num(cx + c)},{num(cy - r)} {num(cx + r)},{num(cy - c)}"
            f" {num(cx + r)},{num(cy)}"
            f"C{num(cx + r)},{num(cy + c)} {num(cx + c)},{num(cy + r)}"
            f" {num(cx)},{num(cy + r)}"
            f"C{num(cx - c)},{num(cy + r)} {num(cx - r)},{num(cy + c)}"
            f" {num(cx - r)},{num(cy)}Z"
        )
    if tag == "rect":
        x, y = float(a.get("x", 0)), float(a.get("y", 0))
        w, h = float(a["width"]), float(a["height"])
        return f"M{num(x)},{num(y)}H{num(x + w)}V{num(y + h)}H{num(x)}Z"
    if tag == "line":
        return f"M{a['x1']},{a['y1']}L{a['x2']},{a['y2']}"
    if tag in ("polyline", "polygon"):
        pts = re.findall(r"[-+]?\d*\.?\d+", a["points"])
        d = "M" + "L".join(f"{pts[i]},{pts[i + 1]}" for i in range(0, len(pts) - 1, 2))
        out = d + "Z" if tag == "polygon" else d
        return out
    return None


def convert(svg_text, filled):
    root = ET.fromstring(svg_text)
    paths = []
    for el in root:
        tag = el.tag.split("}")[-1]
        d = shape_to_path(tag, el.attrib)
        if d:
            paths.append(d)
    if not paths:
        raise ValueError("no drawable shapes found")
    out = [
        '<vector xmlns:android="http://schemas.android.com/apk/res/android"',
        '    android:width="24dp"',
        '    android:height="24dp"',
        '    android:viewportWidth="24"',
        '    android:viewportHeight="24"',
        '    android:tint="?attr/colorControlNormal">',
    ]
    for d in paths:
        out.append("    <path")
        if filled:
            out.append('        android:fillColor="@android:color/white"')
        else:
            out.append('        android:strokeColor="@android:color/white"')
            out.append('        android:strokeWidth="2"')
            out.append('        android:strokeLineCap="round"')
            out.append('        android:strokeLineJoin="round"')
        out.append(f'        android:pathData="{d}"/>')
    out.append("</vector>")
    return "\n".join(out) + "\n"


RENAMES = {
    "circle-ellipsis": "ic_ellipsis_vertical",
    "ellipsis": "ic_ellipsis",
    "arrow-down": "ic_arrow_down",
    "arrow-down-wide-narrow": "ic_sort",
    "audio-lines": "ic_equalizer",
    "circle-alert": "ic_circle_alert",
    "circle-check": "ic_circle_check",
    "circle-plus": "ic_circle_plus",
    "circle-x": "ic_circle_x",
    "chevron-down": "ic_chevron_down",
    "chevron-left": "ic_chevron_left",
    "chevron-right": "ic_chevron_right",
    "cloud-off": "ic_cloud_off",
    "bell": "ic_bell",
    "clock": "ic_clock",
    "download": "ic_download",
    "heart": "ic_heart",
    "house": "ic_home",
    "info": "ic_info",
    "layers": "ic_layers",
    "layout-grid": "ic_grid",
    "list": "ic_list",
    "list-end": "ic_list_end",
    "list-plus": "ic_list_plus",
    "list-start": "ic_list_start",
    "music": "ic_music",
    "pause": "ic_pause",
    "play": "ic_play",
    "plus": "ic_plus",
    "quote": "ic_quote",
    "repeat": "ic_repeat",
    "repeat-1": "ic_repeat_one",
    "rotate-ccw": "ic_retry",
    "search": "ic_search",
    "settings": "ic_settings",
    "share-2": "ic_share",
    "shuffle": "ic_shuffle",
    "skip-back": "ic_skip_back",
    "skip-forward": "ic_skip_forward",
    "smartphone": "ic_phone",
    "square": "ic_square",
    "trash": "ic_trash",
    "wifi-off": "ic_wifi_off",
    "x": "ic_close",
}


def main():
    src_dir, dst_dir = sys.argv[1], sys.argv[2]
    os.makedirs(dst_dir, exist_ok=True)
    count = 0
    for name, res in sorted(RENAMES.items()):
        with open(os.path.join(src_dir, name + ".svg"), encoding="utf-8") as f:
            svg = f.read()
        xml = convert(svg, filled=(name in FILLED))
        with open(os.path.join(dst_dir, res + ".xml"), "w", encoding="utf-8") as f:
            f.write(xml)
        count += 1
    with open(os.path.join(src_dir, "house.svg"), encoding="utf-8") as f:
        svg = f.read()
    with open(os.path.join(dst_dir, "ic_home_selected.xml"), "w", encoding="utf-8") as f:
        f.write(convert(svg, filled=True))
    count += 1
    print(f"wrote {count} drawables to {dst_dir}")


if __name__ == "__main__":
    main()

