#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

uniform vec2 texOffset;

varying vec4 vertColor;
varying vec4 vertTexCoord;

uniform int scale;

void main() {

    vec2 coord = vertTexCoord.xy;

    vec2 size = textureSize(texture, 0);

    float pW = 1/(size.x * scale);
    float pH = 1/(size.y * scale);

    vec2 offX = vec2(pW, 0);
    vec2 offY = vec2(0, pH);

    float alpha = texture2D(texture, coord).a;
    float b0 = texture2D(texture, coord + offX).a;
    float b1 = texture2D(texture, coord - offX).a;
    float b2 = texture2D(texture, coord + offY).a;
    float b3 = texture2D(texture, coord - offY).a;

    float border = b0 * b1 * b2 * b3;

    if((coord + offX).x > 1 || (coord - offX).x < 0 || (coord + offY).y > 1 || (coord - offY).y < 0) {
        border = 0;
    }

    border = (border > 0) ? 1 : 0;
    float a2 = (border == 0 && alpha != 0) ? 1 : alpha;

    vec4 outline = vec4(border, border, border, 1.0f);

    gl_FragColor = texture2D(texture, coord) * vertColor * outline;
    gl_FragColor.a = a2;
}