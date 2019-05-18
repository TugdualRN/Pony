package com.pony.form;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pony.entities.models.News;
import com.pony.entities.models.User;
import com.pony.entities.models.Role;
import com.pony.entities.models.SocialNetwork;
import com.pony.entities.models.Token;
import com.pony.enumerations.SocialNetworkType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserForm extends User {

    Long userId;
    String userName;
    String normalizedUserName;
    @NotBlank String passwordHash;
    @NotBlank String mail;
    String normalizedMail;
    String firstName;
    String lastName;
    String phone;
    boolean isActive;
    boolean isBanned;
    boolean isSuspended;
    LocalDateTime vipEndDate;
    Set<Role> roles;
    List<Token> tokens;
    Map<SocialNetworkType, SocialNetwork> socialNetworks;
    List<News> newsList;

    public UserForm( ) {
    }

    public UserForm(Long userId, String userName, String normalizedUserName, @NotBlank String passwordHash, @NotBlank String mail, String normalizedMail, String firstName, String lastName, String phone, boolean isActive, boolean isBanned, boolean isSuspended, Set<Role> roles) {
        this.userId = userId;
        this.userName = userName;
        this.normalizedUserName = normalizedUserName;
        this.passwordHash = passwordHash;
        this.mail = mail;
        this.normalizedMail = normalizedMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActive = isActive;
        this.isBanned = isBanned;
        this.isSuspended = isSuspended;
        this.roles = roles;
    }

    public UserForm(Long userId,String userName, String normalizedUserName, @NotBlank String passwordHash, @NotBlank String mail, String normalizedMail, String firstName, String lastName, String phone, boolean isActive, boolean isBanned, boolean isSuspended, LocalDateTime vipEndDate, Set<Role> roles, List<Token> tokens, Map<SocialNetworkType, SocialNetwork> socialNetworks, List<News> newsList) {
        this.userId = userId;
        this.userName = userName;
        this.normalizedUserName = normalizedUserName;
        this.passwordHash = passwordHash;
        this.mail = mail;
        this.normalizedMail = normalizedMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.isActive = isActive;
        this.isBanned = isBanned;
        this.isSuspended = isSuspended;
        this.vipEndDate = vipEndDate;
        this.roles = roles;
        this.tokens = tokens;
        this.socialNetworks = socialNetworks;
        this.newsList = newsList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNormalizedUserName() {
        return normalizedUserName;
    }

    public void setNormalizedUserName(String normalizedUserName) {
        this.normalizedUserName = normalizedUserName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNormalizedMail() {
        return normalizedMail;
    }

    public void setNormalizedMail(String normalizedMail) {
        this.normalizedMail = normalizedMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public LocalDateTime getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(LocalDateTime vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Map<SocialNetworkType, SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Map<SocialNetworkType, SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", normalizedUserName='" + normalizedUserName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", mail='" + mail + '\'' +
                ", normalizedMail='" + normalizedMail + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                ", isBanned=" + isBanned +
                ", isSuspended=" + isSuspended +
                ", vipEndDate=" + vipEndDate +
                ", roles=" + roles +
                ", tokens=" + tokens +
                ", socialNetworks=" + socialNetworks +
                ", newsList=" + newsList +
                '}';
    }
}