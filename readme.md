本项目使用的技术栈：
SpringBoot + MyBatis-Plus + MySql + Redis + thymeleaf
```小功能```
```
实时爬取网站信息功能：使用Java.net包下的HttpURLConnection来实时读取目标网站的数据
定时任务（@EnableScheduled + @Scheduled）
缓存功能：使用Redis进行数据的缓存，并且保证这些数据都是热点数据
邮件功能：javax.mail包，发送邮件功能
安全功能：SpringBoot Security安全框架
```
项目网站页面显示的数据是爬取腾讯疫情网站的实时数据







