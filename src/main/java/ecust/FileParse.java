package ecust;

import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileParse {




    //加载所有学生信息
    public static void readSaveList2(List<UserInfo> userInfoList,String fileName) throws Exception{
        File file = ResourceUtils.getFile(fileName);
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String text = null;
        while ((text = bufferedReader.readLine()) != null) {
            UserInfo userInfo = new UserInfo();
            String [] array = text.split("|");
            userInfo.setUserId(array[0]);
            userInfo.setPassword(array[1]);
            userInfoList.add(userInfo);
            // 转成char加到StringBuffer对象中
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

}
