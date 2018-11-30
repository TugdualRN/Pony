package com.pony.business.social;

import java.util.List;

import org.springframework.stereotype.Service;

//import facebook4j.Account;
import facebook4j.Facebook;
//import facebook4j.Post;
//import facebook4j.Reading;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.pony.business.social.ApiService;
import com.pony.entities.dto.ProfileSocialNetworkData;
import com.pony.entities.models.SocialNetwork;
import com.pony.entities.models.User;
import com.pony.enumerations.SocialNetworkType;

/**
 * Class used to manipulate "high level" API interactions (Business oriented)
 */
@Service
public class SocialNetworkService {
    
    private ApiService _apiServie;

    public SocialNetworkService(ApiService apiService) {
        _apiServie = apiService;
    }

    public ProfileSocialNetworkData getSocialData(User user) {
        ProfileSocialNetworkData data = new ProfileSocialNetworkData();
        SocialNetwork twitterData = user.getSocialNetworks().get(SocialNetworkType.TWITTER);

        if (twitterData != null) {
            AccessToken accesToken = new AccessToken(twitterData.getAccesstoken(), twitterData.getTokensecret());

            if (accesToken != null) {
                data.setUser(this.getTwitterUser(accesToken));
                data.setTweets(this.getTwitterTweets(accesToken));
            }
        }

        return data;
    }

    public void getFacebookTest(User user) {
        SocialNetwork facebookData = user.getSocialNetworks().get(SocialNetworkType.FACEBOOK);

        if (facebookData != null) {
            Facebook fb = _apiServie.getFacebook(new facebook4j.auth.AccessToken(facebookData.getAccesstoken(), 3600L));
            

            try {
                //facebook4j.auth.AccessToken test = fb.getOAuthAccessToken();
                //fb = _apiServie.getFacebook(test);
                fb.setOAuthAccessToken(new facebook4j.auth.AccessToken(facebookData.getAccesstoken(), 500L));
                // String userId = fb.getId();
                //facebook4j.ResponseList<Friend> friends = fb.getFeed(new Reading().fields("from"));
                // facebook4j.ResponseList<Post> posts = fb.getFeed(new Reading().fields("id", "message", "from"));
                // facebook4j.ResponseList<Account> acc = fb.getAccounts();

                // facebook4j.ResponseList<Account> accounts = fb.getAccounts();
                // Account yourPageAccount = accounts.get(0);  // if index 0 is your page account.
                // String pageAccessToken = yourPageAccount.getAccessToken();
                
                // for (Friend friend : friends)
                // {
                //     System.out.println(friend.getName());
                // }
            }
            catch (Exception e) {
                System.out.println(e);
                // lol
            }
        }
    }

    private List<Status> getTwitterTweets(AccessToken token) {
        Twitter twitter = _apiServie.getTwitter(token);

        try {
            return twitter.getUserTimeline();
        }
        catch (TwitterException e) {
            return null;
        }
    }

    private twitter4j.User getTwitterUser(AccessToken token) {

        Twitter twitter = _apiServie.getTwitter(token);

        try {
            return twitter.showUser(twitter.getId());
        }
        catch (TwitterException e) {
            return null;
        }
    }
}