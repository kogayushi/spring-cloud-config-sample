# Spring cloud config examples
## Cloud Config Server
### How to set up
#### Dependencies
```groovy
dependencies {
    // mandatory in order to enable cloud config server
    compile('org.springframework.cloud:spring-cloud-config-server')
    
    // for push notification, add followings.
    compile('org.springframework.cloud:spring-cloud-config-monitor')
    compile('org.springframework.cloud:spring-cloud-starter-stream-rabbit') // maybe this is minimum configuration for monitor, including starter-config-bus-amqp is also okay though.
    
    // when you want to secure your server, add following.
    compile('org.springframework.boot:spring-boot-starter-security')
}
```

#### Code
```java
@EnableConfigServer // add this annotation in order to enable cloud config server
@SpringBootApplication
public class CloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerApplication.class, args);
    }
}
```

### Adjusting to someone's situation 
#### When you have to put your configuration file into VCS in public network such as github public repository.
In such situation, let's encrypt credentials.

##### Prerequisites
Encryption and Decryption feature requires `Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files`.

##### What to do
Nothing to do about bootstrap.{yml,properties} file, but ,instead of that, specify encryption key vie environment variable like following, for per environment.

```bash
export ENCRYPT_KEY=mysecretkey
```

Alternative for above, there is a way of using key store file.but I don't like this, cuz it's troublesome to prepare key store for per each environment.

Nevertheless if you want to use key store file, generate key store file then specify it like following.

```yml
encrypt:
  keyStore:
    location: file:./some/path/to/keystore.jks
    password: letmein
    alias: mytestkey
    secret: changeme
```  

#### How to secure your cloud config server with username and password.
Just add spring security into dependencies of `cloud config server`.
Then, configure username and password with apprication.{yml,properties} like following.

```yml
security:
  user:
    name: userName
    password: passw0rd
``` 

After above, your config server requires username and password with Authorization Header.

See curl example.

```bash
curl -X POST -H "Authorization:Basic `echo -n userName:passw0rd | openssl enc -e -base64`" http://localhost:8888/monitor -d "path=cloudClient"
```

## Cloud Config Client
### How to set up
#### Dependencies
```groovy
dependencies {
    // mandatory in order to enable cloud config client
    compile('org.springframework.cloud:spring-cloud-starter-config')
    
    // In order to broad cast reloading of config file, add following.
    compile('org.springframework.cloud:spring-cloud-starter-bus-amqp')
}
```

#### Code
Nothing to do for cloud config client.

#### Adjust to someone's situation
##### if your cloud config server is secured with username and password
Specify username and password, like following.
```yml
spring:
  cloud:
    config:
      uri: http://localhost:8888
      username: userName
      password: passw0rd
```

You don't wanna write production's credentials in configuration file ?

Okay, in such as situation, you can specify it by command line arguments, like following.

```bash
java -jar cloud-config-client.jar --spring.cloud.config.username=userName --spring.cloud.config.password=passw0rd
```

##### wanna read logback.xml from cloud server ?
exclude logback.xml for spring security like this.

when you do this, don't forget disable csrf. 

```java
@EnableConfigServer
@SpringBootApplication
public class CloudServerApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/**/logback*.xml").permitAll()
            .anyRequest().authenticated().and()
            .httpBasic().and()
            .csrf().disable();
    }
}
```
