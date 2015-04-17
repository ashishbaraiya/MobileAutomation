package com.mobileauto.test;

public class UserDO {
  public String username;
  public String email;
  public String password;
  public String name;
  public String programmingLanguage;

  public UserDO(String username, String email, String password, String name,
      String programmingLanguage) {
    super();
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.programmingLanguage = programmingLanguage;
  }
}
