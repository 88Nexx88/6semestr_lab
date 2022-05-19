import java.util.ArrayList;
import java.util.List;

public class Cipher {

    public byte[] sdvig_enc(byte[] txt, List<Integer> key){
        byte[] enc_txt = new byte[txt.length];
        for(int i = 0; i < txt.length; i++){
            enc_txt[i] = right(txt[i], (byte)(int)key.get(i));
        }
        return enc_txt;
    }

    public byte[] sdvig_dec(byte[] txt, List<Integer> key){
        byte[] dec_txt = new byte[txt.length];
        for(int i = 0; i < txt.length; i++){
            dec_txt[i] = left(txt[i], (byte)(int)key.get(i));
        }
        return dec_txt;
    }


    private static byte right(byte a, byte s) {
        int x = a & 0xFF; // Побитовое И с маской 11111111 для устранения расширения знака при приведении отрицательных значений
        int y = s % 8;
        return  (byte)((x >> y) | (x << (8 - y)));
    }
    private static byte left(byte a, byte s) {
        int x = a & 0xFF;
        int y = s % 8;
        return  (byte)((x << y) | (x >> (8 - y)));
    }


    public  byte[] gam_enc(byte[] txt, byte[] key){
        byte[] enc_txt = new byte[txt.length];
        int count = 0;
        for(int i = 0; i < txt.length; i++){
            enc_txt[i] = (byte)((int)txt[i] ^ (int)key[count]);
            count++;
            if(count >= key.length){
                count = 0;
            }
        }
        for(byte b : enc_txt){
            System.out.print(b+" ");

        }
        System.out.println();
        return enc_txt;
    }

    public byte[] gam_dec(byte[] txt, byte[] key){
        byte[] dec_txt = new byte[txt.length];
        int count = 0;
        for(int i = 0; i < txt.length; i++){
            dec_txt[i] = (byte)((int)txt[i] ^ (int)key[count]);
            count++;
            if(count >= key.length){
                count = 0;
            }
        }
        for(byte b : dec_txt){
            System.out.print(b+" ");

        }
        System.out.println();
        return dec_txt;
    }


    public byte[] subm_enc(byte[] txt, byte[] key){
        byte[] txt_enc = new byte[txt.length];
        int count = 0;
        for(byte b : txt){
            for(int i = 0; i < key.length; i++){
                if(b == (byte) i){
                    txt_enc[count] = key[i];
                    break;
                }
            }
            count++;
        }
        for (byte b : txt_enc){
            System.out.print(b+" ");

        }
        System.out.println();
        return txt_enc;
    }

    public byte[] subm_dec(byte[] txt, byte[] key){
        byte[] txt_dec = new byte[txt.length];
        int count = 0;
        for(byte b : txt){
            for(int i = 0; i < key.length; i++){
                if(b == key[i]){
                    txt_dec[count] = (byte)i;
                    break;
                }
            }
            count++;
        }
        for (byte b : txt_dec){
            System.out.print(b+" ");

        }
        System.out.println();
        return txt_dec;
    }


    public byte[] permutation_enc(byte[] txt, List<Integer> key){
        System.out.println("Size: "+txt.length);
        System.out.println("Key: "+key.toString());
        int size_encode_txt = txt.length;
        List<Byte> source = new ArrayList<>();
        if(size_encode_txt % key.size() != 0) {
            while (size_encode_txt % key.size() != 0) {
                size_encode_txt++;
            }
        }
            for(byte b : txt){
                source.add(b);
            }
            while (source.size() != size_encode_txt){
                source.add((byte) 255);
            }

        for(byte b : source){
            System.out.print(b+" ");
        }
        System.out.println();
        byte[] encode_txt = new byte[size_encode_txt];
        List<Integer> tool_key = new ArrayList<>();
        for(int i = 0; i < size_encode_txt / key.size(); i++){
            for(int n : key){
                tool_key.add(n+key.size()*i);
            }
        }
        System.out.println();
        for(int b : tool_key){
            System.out.print(b+" ");
        }
        System.out.println();
        for(int c = 0; c < size_encode_txt; c++){
            encode_txt[c] = source.get(tool_key.get(c));
        }

        return encode_txt;
    }

    public byte[] permutation_dec(byte[] txt, List<Integer> key) {
        System.out.println("Size: "+txt.length);
        System.out.println("Key: "+key.toString());
        int size_decode_txt = txt.length;
        byte[] decode_txt = new byte[size_decode_txt];
        List<Integer> tool_key = new ArrayList<>();
        for(int i = 0; i < size_decode_txt / key.size(); i++){
            for(int n : key){
                tool_key.add(n+key.size()*i);
            }
        }
        System.out.println();
        for(int b : tool_key){
            System.out.print(b+" ");
        }

        for(int i = 0; i < size_decode_txt; i++){
            decode_txt[tool_key.get(i)] = txt[i];
        }
        System.out.println();
        for(byte b : decode_txt){
            System.out.print(b+" ");
        }
        int source_size = size_decode_txt;
        for(int i = size_decode_txt-1; i > size_decode_txt- key.size(); i--){
            if(decode_txt[i] == (byte) 255 && decode_txt[i-1] != (byte) 255){
                source_size = i;
            }
        }
        System.out.println();
        byte[] source_txt = new byte[source_size];
        for(int i = 0; i < source_size; i++){
            source_txt[i] = decode_txt[i];
            System.out.print(source_txt[i]+" ");
        }
        System.out.println();
        return source_txt;
    }
}
