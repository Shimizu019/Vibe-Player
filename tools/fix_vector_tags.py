import re, glob, os
d = r"c:\Users\ADMiN\OneDrive\Desktop\OldPlayer\app\src\main\res\drawable"
fixed = []
for f in glob.glob(os.path.join(d, "*.xml")):
    with open(f, "r", encoding="utf-8") as fh:
        t = fh.read()
    # Close an unterminated <vector ...> opening tag: a vector attribute line ending in
    # a quote immediately followed (next non-space) by a child element <.
    new = re.sub(r'(android:viewportHeight="\d+")(\s*\n\s*<)', r'\1>\2', t)
    if new != t:
        with open(f, "w", encoding="utf-8", newline="") as fh:
            fh.write(new)
        fixed.append(os.path.basename(f))
print("FIXED", len(fixed))
print(", ".join(fixed))
