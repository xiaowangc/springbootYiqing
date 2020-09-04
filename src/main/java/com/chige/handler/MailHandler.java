package com.chige.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

@Component
public class MailHandler {

    @Autowired
    private JavaMailSender mailSender;

    //简单执行一次邮件发送
    public void send(){
        System.out.println("执行邮件发送...");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("来自ChiGe_it真挚的问候");
        simpleMailMessage.setText("广东富婆通讯录已发送至您的秘密邮箱，请查收");
        simpleMailMessage.setTo("LOL_TheChi@163.com");
        simpleMailMessage.setFrom("2440477909@qq.com");
        mailSender.send(simpleMailMessage);
    }

    @Autowired
    private TemplateEngine templateEngine;
    //结合template使用
    public void sendWithTemplate(String title,String content,String toPerson) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject(title);

        //渲染内容到thymeleaf中
        Context context = new Context();
        Map map = new HashMap();
        map.put("content",content);
        map.put("title",title);
        context.setVariables(map);
        String result = templateEngine.process("mailTemplate", context);

        //设置文本 且html标志位true
        helper.setText(result,true);
        helper.setTo(toPerson);
        helper.setFrom("2440477909@qq.com");

//        // 添加附件
//        File file = new File("D:\\简历模板\\王永驰简历.pdf");
//        FileSystemResource resource = new FileSystemResource(file);
//        helper.addAttachment("简历.pdf",resource);

        mailSender.send(mimeMessage);
    }

    //发送发送邮件[可随机发送字符串]
    private static List<String> mailList = new ArrayList<>();
    static {
        mailList.add("人生重要的，不是能力而是性格；不是成功而是价值；不是你认识多少人，而是在你离开人世时，有多少人认识了你！不是他所购买到的，而是他所创造的；不是他所得到的，而是他所付出的；不是他所学到的，而是他所传授的。");

        mailList.add("用快乐去奔跑，用心去倾听，用思维去发展，用努力去奋斗，用目标去衡量，用爱去生活。");

        mailList.add("每个人的一生都有许多梦想，但如果其中一个不断搅扰着你，剩下的就仅仅是行动了。");

        mailList.add("人生是由咸甜苦辣所组成，学会适应，让你的环境变得明亮；学会调节，让你的心情不再忧伤；学会宽容，让你的生活没有烦恼；学会奉献，让你的人生充满阳光。其实天很蓝，阴云总会散；其实海不宽，彼岸连此岸；其实梦很浅，万物皆自然；其实泪也甜，当你心如愿。人生原本就是修行的道场。");

        mailList.add("人生充满了起起落落。关键在于，在顶端时好好享受；在低谷时不失勇气。");

        mailList.add("路，走不通时，学会拐弯，结，解不开时，学会忘记；事，难以做时，学会放下；缘，渐行远时，选择随意。");
    }

    public void sendMailRandom() throws MessagingException {
        String title = "每日一碗鸡汤";
        int index = new Random().nextInt(mailList.size());
        sendWithTemplate(title,mailList.get(index),"2440477909@qq.com");
    }
}
