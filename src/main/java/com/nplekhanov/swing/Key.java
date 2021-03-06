package com.nplekhanov.swing;

public enum Key {
    ENTER(0xa),
    BACK_SPACE(0x8),
    TAB(0x9),
    CANCEL(0x3),
    CLEAR(0xc),
    SHIFT(0x10),
    CONTROL(0x11),
    ALT(0x12),
    PAUSE(0x13),
    CAPS_LOCK(0x14),
    ESCAPE(0x1b),
    SPACE(0x20),
    PAGE_UP(0x21),
    PAGE_DOWN(0x22),
    END(0x23),
    HOME(0x24),
    LEFT(0x25),
    UP(0x26),
    RIGHT(0x27),
    DOWN(0x28),
    COMMA(0x2c),
    MINUS(0x2d),
    PERIOD(0x2e),
    SLASH(0x2f),
    _0(0x30, '0'),
    _1(0x31, '1'),
    _2(0x32, '2'),
    _3(0x33, '3'),
    _4(0x34, '4'),
    _5(0x35, '5'),
    _6(0x36, '6'),
    _7(0x37, '7'),
    _8(0x38, '8'),
    _9(0x39, '9'),
    SEMICOLON(0x3b),
    EQUALS(0x3d),
    A(0x41, 'A'),
    B(0x42, 'B'),
    C(0x43, 'C'),
    D(0x44, 'D'),
    E(0x45, 'E'),
    F(0x46, 'F'),
    G(0x47, 'G'),
    H(0x48, 'H'),
    I(0x49, 'I'),
    J(0x4a, 'J'),
    K(0x4b, 'K'),
    L(0x4c, 'L'),
    M(0x4d, 'M'),
    N(0x4e, 'N'),
    O(0x4f, 'O'),
    P(0x50, 'P'),
    Q(0x51, 'Q'),
    R(0x52, 'R'),
    S(0x53, 'S'),
    T(0x54, 'T'),
    U(0x55, 'U'),
    V(0x56, 'V'),
    W(0x57, 'W'),
    X(0x58, 'X'),
    Y(0x59, 'Y'),
    Z(0x5a, 'Z'),
    OPEN_BRACKET(0x5b),
    BACK_SLASH(0x5c),
    CLOSE_BRACKET(0x5d),
    NUMPAD0(0x60),
    NUMPAD1(0x61),
    NUMPAD2(0x62),
    NUMPAD3(0x63),
    NUMPAD4(0x64),
    NUMPAD5(0x65),
    NUMPAD6(0x66),
    NUMPAD7(0x67),
    NUMPAD8(0x68),
    NUMPAD9(0x69),
    MULTIPLY(0x6a),
    ADD(0x6b),
    SEPARATER(0x6c),
    SEPARATOR(0x6c),
    SUBTRACT(0x6d),
    DECIMAL(0x6e),
    DIVIDE(0x6f),
    DELETE(0x7f),
    NUM_LOCK(0x90),
    SCROLL_LOCK(0x91),
    F1(0x70),
    F2(0x71),
    F3(0x72),
    F4(0x73),
    F5(0x74),
    F6(0x75),
    F7(0x76),
    F8(0x77),
    F9(0x78),
    F10(0x79),
    F11(0x7a),
    F12(0x7b),
    F13(0xf000),
    F14(0xf001),
    F15(0xf002),
    F16(0xf003),
    F17(0xf004),
    F18(0xf005),
    F19(0xf006),
    F20(0xf007),
    F21(0xf008),
    F22(0xf009),
    F23(0xf00a),
    F24(0xf00b),
    PRINTSCREEN(0x9a),
    INSERT(0x9b),
    HELP(0x9c),
    META(0x9d),
    BACK_QUOTE(0xc0),
    QUOTE(0xde),
    KP_UP(0xe0),
    KP_DOWN(0xe1),
    KP_LEFT(0xe2),
    KP_RIGHT(0xe3),
    DEAD_GRAVE(0x80),
    DEAD_ACUTE(0x81),
    DEAD_CIRCUMFLEX(0x82),
    DEAD_TILDE(0x83),
    DEAD_MACRON(0x84),
    DEAD_BREVE(0x85),
    DEAD_ABOVEDOT(0x86),
    DEAD_DIAERESIS(0x87),
    DEAD_ABOVERING(0x88),
    DEAD_DOUBLEACUTE(0x89),
    DEAD_CARON(0x8a),
    DEAD_CEDILLA(0x8b),
    DEAD_OGONEK(0x8c),
    DEAD_IOTA(0x8d),
    DEAD_VOICED_SOUND(0x8e),
    DEAD_SEMIVOICED_SOUND(0x8f),
    AMPERSAND(0x96),
    ASTERISK(0x97),
    QUOTEDBL(0x98),
    LESS(0x99),
    GREATER(0xa0),
    BRACELEFT(0xa1),
    BRACERIGHT(0xa2),
    AT(0x200),
    COLON(0x201),
    CIRCUMFLEX(0x202),
    DOLLAR(0x203),
    EURO_SIGN(0x204),
    EXCLAMATION_MARK(0x205),
    INVERTED_EXCLAMATION_MARK(0x206),
    LEFT_PARENTHESIS(0x207),
    NUMBER_SIGN(0x208),
    PLUS(0x209),
    RIGHT_PARENTHESIS(0x20a),
    UNDERSCORE(0x20b),
    WINDOWS(0x20c),
    CONTEXT_MENU(0x20d),
    FINAL(0x18),
    CONVERT(0x1c),
    NONCONVERT(0x1d),
    ACCEPT(0x1e),
    MODECHANGE(0x1f),
    KANA(0x15),
    KANJI(0x19),
    ALPHANUMERIC(0xf0),
    KATAKANA(0xf1),
    HIRAGANA(0xf2),
    FULL_WIDTH(0xf3),
    HALF_WIDTH(0xf4),
    ROMAN_CHARACTERS(0xf5),
    ALL_CANDIDATES(0x100),
    PREVIOUS_CANDIDATE(0x101),
    CODE_INPUT(0x102),
    JAPANESE_KATAKANA(0x103),
    JAPANESE_HIRAGANA(0x104),
    JAPANESE_ROMAN(0x105),
    KANA_LOCK(0x106),
    INPUT_METHOD_ON_OFF(0x107),
    CUT(0xffd1),
    COPY(0xffcd),
    PASTE(0xffcf),
    UNDO(0xffcb),
    AGAIN(0xffc9),
    FIND(0xffd0),
    PROPS(0xffca),
    STOP(0xffc8),
    COMPOSE(0xff20),
    ALT_GRAPH(0xff7e),
    BEGIN(0xff58),
    UNDEFINED(0x0),
    ;

    public final int code;
    public final char character;

    Key(final int code, final char character) {
        this.code = code;
        this.character = character;
    }

    Key(int code) {
        this.code = code;
        this.character = 0;
    }

    public boolean isTypable() {
        return character != 0;
    }

    public static Key ofAwt(int code) {
        for (final Key value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown key code: " + code);
    }
}
