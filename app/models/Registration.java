package models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.serializer.RegistrationSerializer;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by Pasquale on 19/04/2017.
 */
@Entity(value="Registration", noClassnameStored = true)
@JsonSerialize(using = RegistrationSerializer.class)
public class Registration extends BaseEntity{

    @Reference
    private User user;

    @Reference
    private Repository repository;

    private String apiKey;

    private String username;

    private String password;

    private String token;

    private boolean enabled;

    public Registration() {}

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository( Repository repository ) {
        this.repository = repository;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey( String apiKey ) {
        this.apiKey = apiKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Registration that = (Registration) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (repository != null ? !repository.equals(that.repository) : that.repository != null) return false;
        return apiKey != null ? apiKey.equals(that.apiKey) : that.apiKey == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (repository != null ? repository.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        return result;
    }


}
