package com.appframe.utils.data.encoded;

// hex：
public class Hex {

    // 编码：
    public static String encode(byte bytes[]) {
        char hexString[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };;

        int l = bytes.length;
        char out[] = new char[l << 1];
        int j = 0;
        for (int i = 0; i < l; i++) {
            out[j++] = hexString[(bytes[i] & 0xf0) >>> 4];
            out[j++] = hexString[bytes[i] & 0x0f];
        }
        return new String(out);
    }

    public static byte[] decode(String hex) {
        return decode(hex.toCharArray());
    }

    private static byte[] decode(char data[]) throws IllegalArgumentException {
        int len = data.length;
        if ((len & 1) != 0)
            throw new IllegalArgumentException("Odd number of characters.");
        byte out[] = new byte[len >> 1];
        int i = 0;
        for (int j = 0; j < len;) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 255);
            i++;
        }

        return out;
    }

    private static int toDigit(char ch, int index) throws IllegalArgumentException {
        int digit = Character.digit(ch, 16);
        if (digit == -1)
            throw new IllegalArgumentException((new StringBuilder()).append("Illegal hexadecimal charcter ").append(ch)
                    .append(" at index ").append(index).toString());
        else
            return digit;
    }
}
