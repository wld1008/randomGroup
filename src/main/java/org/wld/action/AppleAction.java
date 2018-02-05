package org.wld.action;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lidan.wu on  2018/1/22 0022.
 */
@RestController
public class AppleAction {

    String allUsers = "李晟，周甜甜，侯文武，黄文平，杨林，陈胜难，黎虎，林源，刘静颐，张小华，詹学宝，李磊，王士昌，张炯，喻永权，韩贝贝，吴潮，谢豪杰，汪志刚，吕同生，董杰，成杰，颜鹏庆，冯辉，高烽，倪晓克，王亚飞，项振旺，陈超，邵康，夏翔，张锡奇，庄永立，徐望超，袁金花，张勇勇，钟昌为，吴佳丽，周锦，陈秀秀，叶贤艳黄志钢，邬思明，刘信，王海超，万栋，王寅生，孙晨豪，林华亨，张景春，吴华辉，程伟，管剑军，叶君军，吴文全，江含斌，武利丹，孙阔陈涛，陈竹，徐光梅，闻锦明，金垚，金泽芬，管梦婷，李超 王萍萍，杨娟，艾卫明，邵昕涛，沈远飞，魏云，于成龙，潘冬，孙誉人，周阳关，姜俊，陆仕杰，单晓曦，王坤建，黄星星，孙力，陈炀，林海强，张燕，陈兵，郭逸群，史晓园，方乃平，占越星，吕琴，龙小桃，冯彦婷，李冠男，岳天壮，阎朋，楼兰婷，余日祥，戴永超，周赵金，堵振东，李海星，黄真珍，胡运瑞，陈永兴，王石生";
    String [] leaders = {"第一组（王泽）","第二组（徐纯）","第三组（叶斌）","第四组（刘盛）","第五组（张汪洋）","第六组(李志勇)","第七组（田野）","第八组（傅腾）"};
    Map<String,String> usermap = new ConcurrentHashMap<String, String>();
    Map<String ,Integer> userMap = new HashMap<String ,Integer>();

    @RequestMapping(value = "/randomShow", method = RequestMethod.GET)
    public String showMessage(){

        return "result.jsp";
    }

    @RequestMapping(value = "/randomLeaders", method = RequestMethod.GET)
    public void print(HttpServletRequest request ,HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=UTF-8");

            if( leaders.length == 0){
                response.getWriter().print("哎呀，来晚了吧，人员已经分配完毕啦，快去联系海星姐姐给你安排位置呦");
                return;
            }

            String username = request.getParameter("userName").trim();

            if(StringUtils.isEmpty(username)){
                response.getWriter().print("急啥，你的名字还没有输入咧");
                return;
            }

            if( !allUsers.contains(username)){
                response.getWriter().print("掐指一算，你肯定没有提前报名吧，快去联系海星姐姐安排分组呦！");
                return;
            }

            if(usermap.containsKey(username)){
                response.getWriter().print(username + "：你已经抽过啦，上次抽取结果：" + usermap.get(username));
                return;
            }
            String leaderName ;
            synchronized (this){
                Random random = new Random();
                int index = random.nextInt(leaders.length);
                leaderName = leaders[index];
                leaders = checkCount(leaders ,leaders[index]);
            }
            response.getWriter().print(username + "：快看你抽的结果：" + leaderName);
            writeTxtFile(username + "抽取的结果：" + leaderName);
            usermap.put(username,leaderName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //校验随机数出现的次数
    private String[] checkCount( String [] leaders ,String leaderName){

            Integer times = userMap.get(leaderName);
            userMap.put(leaderName,times == null ? 1 : ++times);

            if(userMap.get(leaderName) > 13){
                String [] newleaders = remove(leaders,leaderName);
                return newleaders;
            }

        return leaders;
    }

    // 一次只能删除一个元素
    private static String[] remove(String[] arr, String num) {
        String [] tmp = new String[arr.length - 1];

        int idx = 0;
        boolean hasRemove = false;
        for (int i = 0; i < arr.length; i++) {
            if (!hasRemove && arr[i] .equals(num) ) {
                hasRemove = true;
                continue;
            }

            tmp[idx++] = arr[i];
        }
        System.out.println(tmp);
        return tmp;
    }


    private static boolean writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File("E:\\randomUser.txt");
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    return flag;
}

}
