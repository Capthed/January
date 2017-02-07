in vec texcoord;

varying vec4 color;
out vec2 Texcoord

void main() {
    color = gl_Color.rgba;
    gl_Position = ftransform();

    Texcoord = texcoord;
}