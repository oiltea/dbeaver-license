# dbeaver-license
生成 DBeaver 全系列激活许可证，目前支持全系列 `23.0` 版本

## 编译环境
`Oracle JDK 11`

## 使用方法
执行 `main` 方法，根据控制台提示输入相关信息，执行完成会生成 `PUBLIC KEY`、`PRIVATE KEY` 和 `LICENSE`。

## 激活 DBeaver
DBeaver Lite：
修改 `${dbeaver.home}/plugins/com.dbeaver.app.lite_xx.x.x.xxxxxxxxxxxx.jar/keys/dbeaver-le-public.key` 的值为执行 `main` 方法生成的 `PUBLIC KEY` 的值

DBeaver Enterprise：
修改 `${dbeaver.home}/plugins/com.dbeaver.app.enterprise_xx.x.x.xxxxxxxxxxxx.jar/keys/dbeaver-ee-public.key` 的值为执行 `main` 方法生成的 `PUBLIC KEY` 的值

DBeaver Ultimate：
修改 `${dbeaver.home}/plugins/com.dbeaver.app.ultimate_xx.x.x.xxxxxxxxxxxx.jar/keys/dbeaver-ue-public.key` 的值为执行 `main` 方法生成的 `PUBLIC KEY` 的值

新建目录 `${user.home}/.jkiss-lm`
在 `.jkiss-lm` 下新建文件 `public-key.txt`，内容为生成的 `PUBLIC KEY` 的值
在 `.jkiss-lm` 下新建文件 `private-key.txt`，内容为生成的 `PRIVATE KEY` 的值

完成后启动 `DBeaver` import-license 输入执行 `main` 方法生成的 `LICENSE`。

## 联网激活失效
结合 [ja-netfilter](https://github.com/oiltea/ja-netfilter) 解决联网激活失效的问题。

`ja-netfilter/config/dns.conf`
```
[DNS]
EQUAL,dbeaver.com
```

`ja-netfilter/config/url.conf`
```
[URL]
PREFIX,https://dbeaver.com/lmp/checkLicense
```

`${dbeaver.home}/dbeaver.ini`
```
-javaagent:${ja-netfilter.home}/ja-netfilter.jar
```