import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

public class Program {
    public static void main(String[] args) throws IOException {
        List<String> alg = new ArrayList<>();
        alg.add("subm");
        alg.add("perm");
        alg.add("gamm");
        Tools tools = new Tools();
        GeneratedKey generatedKey = new GeneratedKey();
        Cipher cipher = new Cipher();
        byte[] txt = tools.readFromFile(args[0]);
        //int count_Round = StdRandom.uniform(10, 20);
        int count_Round = 16;
        //int count_Round = 4;
        int[] sub_var = {2, 3};
        int[] per_var = {2, 3};
        int[] gam_var = {0, 3};
        int[] block_var = {0, 2};
        List<int[]> variants = new ArrayList<>();
        variants.add(sub_var);
        variants.add(per_var);
        variants.add(gam_var);
        variants.add(block_var);///////////////////
        int fr = StdRandom.uniform(4);///////////!!!!!!!!!!!!
        int[] porydok = new int[count_Round];
        for(int i = 0; i < count_Round; i++){
            porydok[i] = fr;
            fr = variants.get(fr)[StdRandom.uniform(variants.get(fr).length)];
        }
        for(int i : porydok){
            System.out.print(i+" ");
        }
        System.out.println();
        List<byte[]> keys = new ArrayList<>();
        for(int i = 0; i < count_Round; i++){
            if(porydok[i] == 0) {
                System.out.println("___________________");
                byte[] key_sub = generatedKey.substitution();
                keys.add(key_sub);
                txt = cipher.subm_enc(txt, key_sub);
                System.out.println("___________________");
            }
            else if(porydok[i] == 1){
                System.out.println("___________________");
                List<Integer> key_per = generatedKey.permutation(StdRandom.uniform(4, Integer.parseInt(args[1])));
                byte[] key_per1 = new byte[key_per.size()];
                int c = 0;
                for(int b : key_per){
                    key_per1[c] = (byte) b;
                    c++;
                }
                keys.add(key_per1);
                txt = cipher.permutation_enc(txt, key_per);
                System.out.println("___________________");
            }
            else if(porydok[i] == 2){
                System.out.println("___________________");
                byte[] key_gam = generatedKey.gammirov(StdRandom.uniform(4, Integer.parseInt(args[1])));
                keys.add(key_gam);
                txt = cipher.gam_enc(txt, key_gam);
                System.out.println("___________________");
            }
            else if(porydok[i] == 3){
                System.out.println("___________________");
                List<Integer> key_sdv = generatedKey.sdvig(txt.length);
                byte[] key_sdvb = new byte[key_sdv.size()];
                int c = 0;
                for(int d : key_sdv){
                    key_sdvb[c] = (byte) d;
                    c++;
                }
                keys.add(key_sdvb);
                txt = cipher.sdvig_enc(txt, key_sdv);
                System.out.println("___________________");
            }
        }
        System.out.println();
        for(byte b : txt){
            System.out.print(b+" ");
        }
        System.out.println();
        System.out.println("Данные в файл: ");
        System.out.println("Последовательность: ");
        for(int i : porydok){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Ключи: ");
        for(byte[] bytes : keys){
            System.out.println("Key: ");
            for(byte b : bytes){
                System.out.print(b+" ");
            }
            System.out.println();
        }
        String[] name_file = new String[2];;
        if(args[0].contains(".")){
            name_file = args[0].split("\\.");
        }
        else {
            name_file[0] = args[0];
            name_file[1] = "";
        }
        for(String s : name_file){
            System.out.println(s+" ");
        }
        tools.writeToFile(name_file[0]+"_enc"+"."+name_file[1], txt);

        tools.writeVectorKey(keys);


        for(int i = porydok.length - 1; i >= 0; i--){
            if(porydok[i] == 0) {
                System.out.println("___________________");
                byte[] key_sub = keys.get(i);
                txt = cipher.subm_dec(txt, key_sub);
                System.out.println("___________________");
            }
            else if(porydok[i] == 1){
                System.out.println("___________________");
                List<Integer> key_per = new ArrayList<>();
                for(byte b : keys.get(i)){
                    key_per.add((int) b);
                }
                txt = cipher.permutation_dec(txt, key_per);
                System.out.println("___________________");
            }
            else if(porydok[i] == 2){
                System.out.println("___________________");
                byte[] key_gam = keys.get(i);
                txt = cipher.gam_dec(txt, key_gam);
                System.out.println("___________________");
            }
            else if(porydok[i] == 3){
                System.out.println("___________________");
                byte[] key_sdv = keys.get(i);
                List<Integer> key_sdvi = new ArrayList<>();
                for(byte d : key_sdv){
                    key_sdvi.add((int) d);
                }
                txt = cipher.sdvig_dec(txt, key_sdvi);
                System.out.println("___________________");
            }
        }
        System.out.println("Итог: ");
        for(byte t : txt){
            System.out.print(t + " ");
        }
        tools.writeToFile(name_file[0]+"_dec"+"."+name_file[1], txt);

    }
}
