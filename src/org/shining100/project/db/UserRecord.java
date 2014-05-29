package org.shining100.project.db;

/**
 * Created by yangguang on 2014/5/28.
 */
public class UserRecord {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    int id;
    String name;
    String password;
}
