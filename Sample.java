import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
//import java.io.FileNotFoundException;

public class Sample {
    public static void main(String[] args) throws FileNotFoundException {
        File file= new File ("C:\\Users\\Shanmu\\IdeaProjects\\Email_Client\\src\\Clients_List.txt");
        Scanner sc = new Scanner (file);
        ArrayList<String> dataList = new ArrayList<String>( );

        while (sc.hasNextLine()) {
            dataList.add(sc.nextLine());
        }
        for (int i=0; i< dataList.size();i++){
            String[] dataSplitArray1= dataList.get(i).split(":");
            System.out.println(dataList);
            String[] dataSplitArray2=dataSplitArray1[1].split(",");
            if (dataList.get(i).startsWith("Office_friend:")){
                String name= dataSplitArray2[0];
                String email=dataSplitArray2[1];
                String designation=dataSplitArray2[2];
                String dob=dataSplitArray2[3];

            }else if (dataList.get(i).startsWith("Personal")){
                String name= dataSplitArray2[0];
                String nickname=dataSplitArray2[1];
                String email=dataSplitArray2[2];
                String dob=dataSplitArray2[3];

            }else if (dataList.get(i).startsWith("Official:")){
                String name= dataSplitArray2[0];
                String email=dataSplitArray2[1];
                String designation=dataSplitArray2[2];
            }
        }


    }
}
