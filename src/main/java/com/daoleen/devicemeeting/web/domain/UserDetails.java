package com.daoleen.devicemeeting.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

/**
 * Created by alex on 9.7.14.
 */

@Entity
@Table(name = "user_details")
@Transactional
public class UserDetails implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2618838865503753251L;

	@Size(max = 32)
    @Column(name = "first_name", length = 32)
    private String firstName;

    @Size(max = 32)
    @Column(name = "last_name", length = 32)
    private String lastName;

    @Size(max = 1024)
    @Column(name = "about", length = 1024)
    private String about;

    @Column(name = "is_online", length = 1)
    private boolean online;

    @Size(max = 32)
    @Column(name = "skype", length = 32)
    private String skype;

    @Size(max = 32)
    @Column(name = "linkedin", length = 255)
    private String linkedin;

    @Column(name = "rating")
    private float rating;

    @Column(name = "rating_votes_count")
    private int ratingVotesCount;

    @Id
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserDetails() {
    }

    @Override
    public String toString() {
        if(Strings.isNullOrEmpty(firstName) && Strings.isNullOrEmpty(lastName)) {
            return null;
        }
        return String.format("%s %s", getFirstName(), getLastName());
    }

    // Getters/Setters
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingVotesCount() {
        return ratingVotesCount;
    }

    public void setRatingVotesCount(int ratingVotesCount) {
        this.ratingVotesCount = ratingVotesCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
