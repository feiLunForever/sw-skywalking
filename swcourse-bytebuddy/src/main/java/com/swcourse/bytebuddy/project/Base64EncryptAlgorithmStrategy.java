package com.swcourse.bytebuddy.project;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-13 17:27
 **/
public class Base64EncryptAlgorithmStrategy implements StrEncryptAlgorithmStrategy {

    private static char[] toBase64;

    private HashSet<Character> key = new HashSet<>();


    private static final int[] fromBase64 = new int[256];

    private static final char[] base64Code = {'n', 'o', 'p', 'q', 'r', 's', 'x', 'y', 'z', 'C', 'D', 'L', 'M', '0', '1', 't', 'u', 'v', 'w', '2', '3', '4', '5', '6', 'X', 'N', 'O', 'P', 'Q', 'V', 'W', 'Y', 'Z', 'i', 'j', 'k', 'l', 'm', 'a', 'b', 'c', 'd', 'R', 'S', 'T', 'U', 'e', 'f', 'g', 'h', '7', '8', '9', '+', '/', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'A', 'B',};

    @Override
    public String encrypt(String source, Charset charset, String secretKey) {
        createKey(secretKey);
        byte[] bytes = source.getBytes(charset);
        return new String(bytes, charset);
    }

    @Override
    public String decrypt(String source, Charset charset, String secretKey) {
        createKey(secretKey);
        byte[] decode = decode(source.getBytes(charset));
        return new String(decode, charset);
    }

    @Override
    public byte[] encrypt(byte[] source, String secretKey) {
        createKey(secretKey);
        return encode(source);
    }

    @Override
    public byte[] decrypt(byte[] source, String secretKey) {
        createKey(secretKey);
        return decode(source);
    }

    private void createKey(String secretKey) {
        if (secretKey == null) {
            throw new RuntimeException("key 为空");
        }
        if (key.isEmpty()) {
            char[] chars = secretKey.toCharArray();
            for (char aChar : chars) {
                if (aChar == '=' || aChar == '*' || aChar == '#' || aChar == '.') {
                    continue;
                }
                key.add(aChar);
            }
            List<Character> list = new ArrayList<>();
            list.addAll(key);
            for (char c : base64Code) {
                if (!list.contains(c)) {
                    list.add(c);
                }
            }
            toBase64 = new char[list.size()];
            for (int i = 0; i < list.size(); i++) {
                toBase64[i] = list.get(i);
            }
        }
        Arrays.fill(fromBase64, -1);
        for (int i = 0; i < toBase64.length; i++) {
            fromBase64[toBase64[i]] = i;
        }
        fromBase64['='] = -2;
    }

    public byte[] encode(byte[] src) {
        int len = outLength(src.length);          // dst array size
        byte[] dst = new byte[len];
        int ret = encode0(src, 0, src.length, dst);
        if (ret != dst.length) {
            return Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    private final int outLength(int srclen) {
        int len = 0;
        len = 4 * ((srclen + 2) / 3);
        return len;
    }

    private int encode0(byte[] src, int off, int end, byte[] dst) {
        char[] base64 = toBase64;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                int bits = (src[sp0++] & 0xff) << 16 | (src[sp0++] & 0xff) << 8 | (src[sp0++] & 0xff);
                dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                dst[dp0++] = (byte) base64[bits & 0x3f];
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;
        }
        if (sp < end) {               // 1 or 2 leftover bytes
            int b0 = src[sp++] & 0xff;
            dst[dp++] = (byte) base64[b0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];

                dst[dp++] = '=';
                dst[dp++] = '=';

            } else {
                int b1 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
                dst[dp++] = '=';

            }
        }
        return dp;
    }


    public byte[] decode(byte[] src) {
        byte[] dst = new byte[outLength(src, 0, src.length)];
        int ret = decode0(src, 0, src.length, dst);
        if (ret != dst.length) {
            dst = Arrays.copyOf(dst, ret);
        }
        return dst;
    }

    private int outLength(byte[] src, int sp, int sl) {
        int[] base64 = fromBase64;
        int paddings = 0;
        int len = sl - sp;
        if (len == 0) {
            return 0;
        }
        if (len < 2) {

        }

        if (src[sl - 1] == '=') {
            paddings++;
            if (src[sl - 2] == '=') {
                paddings++;
            }
        }

        if (paddings == 0 && (len & 0x3) != 0) {
            paddings = 4 - (len & 0x3);
        }
        return 3 * ((len + 3) / 4) - paddings;
    }

    private int decode0(byte[] src, int sp, int sl, byte[] dst) {
        int[] base64 = fromBase64;
        int dp = 0;
        int bits = 0;
        int shiftto = 18;       // pos of first byte of 4-byte atom
        while (sp < sl) {
            int b = src[sp++] & 0xff;
            if ((b = base64[b]) < 0) {
                if (b == -2) {         // padding byte '='
                    // =     shiftto==18 unnecessary padding
                    // x=    shiftto==12 a dangling single x
                    // x     to be handled together with non-padding case
                    // xx=   shiftto==6&&sp==sl missing last =
                    // xx=y  shiftto==6 last is not =
                    if (shiftto == 6 && (sp == sl || src[sp++] != '=') || shiftto == 18) {
                        throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
                    }
                    break;
                }

            }
            bits |= (b << shiftto);
            shiftto -= 6;
            if (shiftto < 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
                dst[dp++] = (byte) (bits);
                shiftto = 18;
                bits = 0;
            }
        }
        // reached end of byte array or hit padding '=' characters.
        if (shiftto == 6) {
            dst[dp++] = (byte) (bits >> 16);
        } else if (shiftto == 0) {
            dst[dp++] = (byte) (bits >> 16);
            dst[dp++] = (byte) (bits >> 8);
        } else if (shiftto == 12) {
            // dangling single "x", incorrectly encoded.
            throw new IllegalArgumentException("Last unit does not have enough valid bits");
        }
        // anything left is invalid, if is not MIME.
        // if MIME, ignore all non-base64 character
        while (sp < sl) {
            if (base64[src[sp++]] < 0) {
                continue;
            }
            throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + sp);
        }
        return dp;
    }


}
