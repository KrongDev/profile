# Profile

유저와 관련된 작업들을 수행하는 서비스입니다.

## 1. Spring Security
인증인가 처리는 Spring Security를 사용하여 적용하였습니다.  


아래는 HttpSecurity의 option입니다.

| 설정                           | 설명                                                 |  
|:-----------------------------|:---------------------------------------------------|
| http.authorizeRequests()     | URL 경로에 대한 인가 규칙을 설정합니다.                           |  
| .requestMatchers().permitAll()   | 특정 URL 경로를 인증 없이 허용합니다.                            |  
| .anyRequest().authenticated()| 모든 요청에 대해 인증을 요구합니다.                               |  
| .hasRole()                   | 특정 역할을 가진 사용자만 접근을 허용합니다.                          |  
| .formLogin()                 | 폼 기반 로그인을 활성화합니다.                                  |  
| .loginPage()                 | 로그인 페이지의 경로를 지정합니다.                                |  
| .defaultSuccessUrl()         | 로그인 성공 후 이동할 기본 URL을 설정합니다.                        |  
| .logout()                    | 로그아웃을 처리하는 설정을 추가합니다.                              |  
| .logoutUrl()                 | 로그아웃 URL을 지정합니다.                                   |  
| .logoutSuccessUrl()          | 로그아웃 성공 후 이동할 URL을 설정합니다.                          |  
| .csrf()                      | CSRF(Cross-Site Request Forgery) 공격 방어 설정을 활성화합니다. |  
| .sessionManagement()         | 세션 관리를 설정합니다.                                      |  
| .sessionCreationPolicy()     | 세션 생성 정책을 설정합니다.                                   |  
