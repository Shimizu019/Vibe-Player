#!/usr/bin/env python3
"""Generate legacy launcher-icon PNG fallbacks (spec section 4).

Android 7.x (API 24-25) has no adaptive icons, so @mipmap/ic_launcher and
@mipmap/ic_launcher_round must resolve to real bitmaps there. We draw the same
gold record on #1E1915 used by the adaptive-icon foreground, at the standard
legacy launcher sizes. Run from the repo root:

    python tools/make_launcher_pngs.py
"""
import os
from PIL import Image, ImageDraw

GOLD = (0xD4, 0xA7, 0x3A, 255)
ESPRESSO = (0x1E, 0x19, 0x15, 255)
TRANSPARENT = (0, 0, 0, 0)

# Legacy launcher bitmap edge lengths per density bucket.
SIZES = {
    "mipmap-mdpi": 48,
    "mipmap-hdpi": 72,
    "mipmap-xhdpi": 96,
    "mipmap-xxhdpi": 144,
    "mipmap-xxxhdpi": 192,
}

RES = "app/src/main/res"


def draw_record(size: int, rounded: bool) -> Image.Image:
    img = Image.new("RGBA", (size, size), TRANSPARENT)
    d = ImageDraw.Draw(img)
    if rounded:
        # Round variant: opaque espresso disc, so the corners clip to circular.
        r = size / 2
        d.ellipse([0, 0, size - 1, size - 1], fill=ESPRESSO)
    else:
        d.rectangle([0, 0, size - 1, size - 1], fill=ESPRESSO)

    # Record geometry mirrors ic_launcher_foreground (108 viewport, centre 54,54):
    # rings at r=32 (w5), r=23 (w4), r=14 (w3), filled centre r=9.
    scale = size / 108.0
    cx = cy = size / 2

    def ring(radius, width):
        rr = radius * scale
        ww = max(1, int(round(width * scale)))
        d.ellipse(
            [cx - rr, cy - rr, cx + rr, cy + rr],
            outline=GOLD,
            width=ww,
        )

    ring(32, 5)
    ring(23, 4)
    ring(14, 3)
    cr = 9 * scale
    d.ellipse([cx - cr, cy - cr, cx + cr, cy + cr], fill=GOLD)
    return img


def main() -> None:
    for folder, size in SIZES.items():
        out_dir = os.path.join(RES, folder)
        os.makedirs(out_dir, exist_ok=True)
        draw_record(size, rounded=False).save(os.path.join(out_dir, "ic_launcher.png"))
        draw_record(size, rounded=True).save(os.path.join(out_dir, "ic_launcher_round.png"))
        print(f"wrote {folder}/ic_launcher.png + ic_launcher_round.png ({size}px)")


if __name__ == "__main__":
    main()
