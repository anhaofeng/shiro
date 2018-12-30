#####依赖包
```
<dependency>
	<groupId>org.apache.shiro</groupId>
	<artifactId>shiro-spring</artifactId>
	<version>1.3.2</version>
</dependency>
```
#####Config
```config中 SecurityManager 类导入的应该是 import org.apache.shiro.mgt.SecurityManager;但是，如果是复制代码过来的话，会默认导入
  java.lang.SecurityManagershirFilter 方法中主要是设置了一些重要的跳转 url，
  比如未登陆时，无权限时的跳转；以及设置了各类 url 的权限拦截，
  比如 /user 开始的 url 需要 user 权限，/admin 开始的 url 需要 admin 权限等.
  
  当运行一个Web应用程序时，Shiro将会创建一些有用的默认 Filter 实例，并自动地将
  它们置为可用，而这些默认的 Filter 实例是被 DefaultFilter 枚举类定义的，当然
  我们也可以自定义 Filter 实例
  ```
#####参数
````
  anon
  无参，开放权限，可以理解为匿名用户或游客
  
  authc
  无参，需要认证
  
  logout
  无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
  
  authcBasic
  无参，表示 httpBasic 认证
  
  user
  无参，表示必须存在用户，当登入操作时不做检查
  
  ssl
  无参，表示安全的URL请求，协议为 https
  
  perms[user]
  参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms["user, admin"]，当有多个参数时必须每个参数都通过才算通过
  
  roles[admin]
  参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles["admin，user"]，当有多个参数时必须每个参数都通过才算通过
  
  rest[user]
  根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
  
  port[8081]
  当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数
  常用的主要就是 anon，authc，user，roles，perms 等
  注意：anon, authc, authcBasic, user 是第一组认证过滤器，perms, port, rest, roles, ssl 是第二组授权过滤器，要通过授权过滤器，就先要完成登陆认证操作（即先要完成认证才能前去寻找授权) 才能走第二组授权器（例如访问需要 roles 权限的 url，如果还没有登陆的话，会直接跳转到 shiroFilterFactoryBean.setLoginUrl(); 设置的 url ）
  
  ````
#####自定义realm 类
````
我们首先要继承 AuthorizingRealm 类来自定义我们自己的 realm 以进行我们自定义的身份，权限认证操作。
记得要 Override 重写 doGetAuthenticationInfo 和 doGetAuthorizationInfo 两个方法（两个方法名很相似，不要搞错）
重写的两个方法分别是实现身份认证以及权限认证，shiro 中有个作登陆操作的 Subject.login() 方法，当我们把封装了用户名，密码的 token 作为参数传入，便会跑进这两个方法里面（不一定两个方法都会进入）
其中 doGetAuthorizationInfo 方法只有在需要权限认证时才会进去，比如前面配置类中配置了 filterChainDefinitionMap.put("/admin/**", "roles[admin]"); 的管理员角色，这时进入 /admin 时就会进入 doGetAuthorizationInfo 方法来检查权限；而 doGetAuthenticationInfo 方法则是需要身份认证时（比如前面的 Subject.login() 方法）才会进入
再说下 UsernamePasswordToken 类，我们可以从该对象拿到登陆时的用户名和密码（登陆时会使用 new UsernamePasswordToken(username, password);），而 get 用户名或密码有以下几个方法
token.getUsername()  //获得用户名 String
token.getPrincipal() //获得用户名 Object 
token.getPassword()  //获得密码 char[]
token.getCredentials() //获得密码 Object

````
#######注意：有很多人会发现，UserMapper 等类，接口无法通过 @Autowired 注入进来，跑程序的时候会报 NullPointerException，网上说了很多诸如是 Spring 加载顺序等原因，但其实有一个很重要的地方要大家注意，CustomRealm 这个类是在 shiro 配置类的 securityManager.setRealm() 方法中设置进去的，而很多人直接写securityManager.setRealm(new CustomRealm()); ,这样是不行的，必须要使用 @Bean 注入 MyRealm，不能直接 new 对象：
       
    
  
  