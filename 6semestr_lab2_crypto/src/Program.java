import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//1324

public class Program {
    public static void main(String[] args) throws IOException {
        Tools tools = new Tools();
        GeneratedKey generatedKey = new GeneratedKey();
        switch (args[0]){
            case "p":
                //p 6
                byte[] txt = tools.readFromFile("perm/"+args[2]);
                List<Integer> key =  generatedKey.permutation(Integer.parseInt(args[1]));
                byte[] txt_enc = permutation_enc(txt, key);
                tools.writeToFile("perm/"+args[2]+"_enc", txt_enc);

                tools.writeToFileSymbol("perm/key", key);
                List<Integer> key_dec = tools.readFromFileSymbol("perm/key");

                byte[] source_txt = permutation_dec(txt_enc, key_dec);
                tools.writeToFile("perm/"+args[2]+"_dec", source_txt);
                break;
            case "decode":
                txt = tools.readFromFile("sub/"+args[2]);
                byte[] key_sub_enc = tools.readFromFile("sub/key");
                List<Byte> keySUB = new ArrayList<>();
                for(byte b : key_sub_enc){
                    keySUB.add(b);
                }
                byte[] txt_dec_sub = subm_dec(txt, keySUB);
                tools.writeToFile("sub/dec_"+args[2], txt_dec_sub);
                break;
            case "s":
                txt = tools.readFromFile("sub/"+args[2]);
                List<Byte> key_sub =  generatedKey.substitution();
                byte[] key_sub1 = new byte[key_sub.size()];
                int c = 0;
                for (byte b : key_sub){
                    key_sub1[c] = b;
                    c++;
                }
                tools.writeToFile("sub/key", key_sub1);
                key_sub_enc = tools.readFromFile("sub/key");

                byte[] txt_enc_sub = subm_enc(txt, key_sub_enc);
                tools.writeToFile("sub/"+args[2]+"_enc", txt_enc_sub);
                txt_dec_sub = subm_dec(txt_enc_sub, key_sub);
                tools.writeToFile("sub/"+args[2]+"_dec", txt_dec_sub);
                break;
            case "g":
                txt = tools.readFromFile("gam/"+args[2]);
                byte[] key_gam =  generatedKey.gammirov(Integer.parseInt(args[1]));
                tools.writeToFile("gam/key", key_gam);
                byte[] key_gam_enc = tools.readFromFile("gam/key");

                byte[] txt_enc_gam = gam_enc(txt, key_gam_enc);
                tools.writeToFile("gam/"+args[2]+"_enc", txt_enc_gam);
                byte[] txt_dec_gam = gam_dec(txt_enc_gam, key_gam);
                tools.writeToFile("gam/"+args[2]+"_dec", txt_dec_gam);
                break;
            case "v":
                txt = tools.readFromFile("ver/"+args[2]);
                byte[] key_ver =  generatedKey.gammirov(txt.length);
                tools.writeToFile("ver/key", key_ver);
                byte[] key_ver_enc = tools.readFromFile("ver/key");

                byte[] txt_enc_ver = gam_enc(txt, key_ver_enc);
                tools.writeToFile("ver/"+args[2]+"_enc", txt_enc_ver);
                byte[] txt_dec_ver = gam_dec(txt_enc_ver, key_ver);
                tools.writeToFile("ver/"+args[2]+"_dec", txt_dec_ver);
                break;
            case "r":
                txt = tools.readFromFile("round/"+args[2]);
                System.out.print("ORIGINAL  TXT ");
                for(byte b : txt){
                    System.out.print(b+" ");
                }
                System.out.println();
                key_sub =  generatedKey.substitution();
                key_sub1 = new byte[key_sub.size()];
                c = 0;
                for (byte b : key_sub){
                    key_sub1[c] = b;
                    c++;
                }
                for(byte b : key_sub1){
                    System.out.print(b+" ");
                }
                System.out.println();
                System.out.println("1!");
                txt_enc = subm_enc(txt, key_sub1);//1
                for(byte b : txt_enc){
                    System.out.print(b+" ");
                }
                System.out.println();

                key_gam =  generatedKey.gammirov(Integer.parseInt(args[1]));
                for(byte b : key_gam){
                    System.out.print(b+" ");
                }
                System.out.println();
                txt_enc_gam = gam_enc(txt_enc, key_gam);//2
                for(byte b : txt_enc_gam){
                    System.out.print(b+" ");
                }
                System.out.println();

                List<Integer> key_per =  generatedKey.permutation(Integer.parseInt(args[1]));
                for(int b : key_per){
                    System.out.print(b+" ");
                }
                System.out.println();
                byte[] txt_enc_per = permutation_enc(txt_enc_gam, key_per);//3
                for(byte b : txt_enc_per){
                    System.out.print(b+" ");
                }
                System.out.println();

                key_ver =  generatedKey.gammirov(txt_enc_per.length);
                for(byte b : key_ver){
                    System.out.print(b+" ");
                }
                System.out.println();
                txt_enc_ver = gam_enc(txt_enc_per, key_ver);//4
                for(byte b : txt_enc_ver){
                    System.out.print(b+" ");
                }
                System.out.println();



                tools.writeToFile("round/"+args[2]+"_enc", txt_enc_ver);
                List<byte []> all_key = new ArrayList<>();
                all_key.add(key_sub1);
                all_key.add(key_gam);
                byte[] key_per1 = new byte[key_per.size()];
                int count = 0;
                for(int i  : key_per){
                    key_per1[count] = (byte) i;
                    count++;
                }
                all_key.add(key_per1);
                all_key.add(key_ver);
                tools.writeVectorKey(all_key);


                txt_dec_ver = gam_dec(txt_enc_ver, key_ver);//4
                byte[] txt_dec_per = permutation_dec(txt_dec_ver, key_per);//3
                txt_dec_gam = gam_dec(txt_dec_per, key_gam);//2
                byte[] txt_dec = subm_dec(txt_dec_gam, key_sub);//1
                tools.writeToFile("round/"+args[2]+"_dec", txt_dec);
                break;

        }
    }
    public static byte[] gam_enc(byte[] txt, byte[] key){
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

    public static byte[] gam_dec(byte[] txt, byte[] key){
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


    public static byte[] subm_enc(byte[] txt, byte[] key){
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

    public static byte[] subm_dec(byte[] txt, List<Byte> key){
        byte[] txt_dec = new byte[txt.length];
        int count = 0;
        for(byte b : txt){
            for(int i = 0; i < key.size(); i++){
                if(b == key.get(i)){
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


    public static byte[] permutation_enc(byte[] txt, List<Integer> key){
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

        for(int c = 0; c < size_encode_txt; c++){
            encode_txt[c] = source.get(tool_key.get(c));
        }
        for(int b : encode_txt){
            System.out.print(b+" ");
        }
        System.out.println();
        return encode_txt;
    }

    public static byte[] permutation_dec(byte[] txt, List<Integer> key) {
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

class GeneratedKey{
    public List<Byte> substitution(){
        List<Byte> key = new ArrayList<>();
        for(int i = 0; i < 256; i++){
            key.add((byte)i);
        }
        Collections.shuffle(key);
        for(byte b : key){
            System.out.print(b+" ");
        }
        System.out.println();
        return key;
    }

    public List<Integer> permutation(int N){
        List<Integer> key = new ArrayList<>();
        for(int i = 0; i < N; i++){
            key.add(i);
        }
        Collections.shuffle(key);
        return key;
    }

    public byte[] gammirov(int N){
        byte[] key = new byte[N];
        for(int i = 0; i < key.length; i++){
            key[i] = (byte)StdRandom.uniform(0, 256);
        }
        for(byte b : key){
            System.out.print(b+" ");
        }
        System.out.println();
        return key;
    }
}
