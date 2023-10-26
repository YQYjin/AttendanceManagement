然后，在 Spring Security 配置中，确保 /user/{userID} 路径被正确授权，用户具有 "USER" 权限：

java
Copy code
http
.authorizeRequests()
.antMatchers("/").permitAll()
.antMatchers("/user/**").hasRole("USER")
.and()
// ...
请注意，hasRole("USER") 表示用户需要具有 "ROLE_USER" 这个角色，不是 "USER"。通常，Spring Security 会自动添加 "ROLE_" 前缀，因此你可以使用 "USER"，但实际上它会匹配 "ROLE_USER"。

如果你的 SimpleGrantedAuthority 是 "USER"，请将 hasRole("USER") 更改为 hasAuthority("USER")：

java
Copy code
http
.authorizeRequests()
.antMatchers("/").permitAll()
.antMatchers("/user/**").hasAuthority("USER")
.and()
// ...
这会要求用户具有 "USER" 权限而不是 "ROLE_USER" 角色。确保你的权限授予与配置一致，以便用户可以成功访问 /user/{userID} 路径。