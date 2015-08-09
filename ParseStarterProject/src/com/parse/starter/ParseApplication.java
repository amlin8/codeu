package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "WqSRBU0fp8mSY42w8nHvewgif0oGkV0IUbvVdp1Z", "uSgumivn3vVgkHytIl66JvGrt03RavlfWMwA7A60");


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);


    setContentView(R.layout.main);
  }


  //function for logging in user
  public void logInUser(String username, String password) {
    ParseUser.logInInBackground(username, password, new LogInCallback() {
        public void done(ParseUser user, ParseException e) {
            if (user != null) {
                //Code for user being logged in.
            }
            else {
                //Signup failed. Look at the ParseException to see what happened. 
            }
        }
    });
  }

  //function for signing up user
  public void signUpUser(String username, String password, String email) {
    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);

    //can add more information

    user.signUpInBackground(new SignUpCallback() {
        public void done(ParseException e) {
            if (e==null) {
                //Hooray! Let them use the app now.
            }
            else {
                //Sign up didn't succeed. Look at the ParseException to figure out what went wrong. 
            }
        }
    });
  }

  //function for logging out user
  public void logOutUser() {
    ParseUser.logOut();
  }

  //function for saving the safety report,
  //should be called once user submits safety report 
  public void createSafetyReport(String d, int latitude, int longitude) {
    ParseObject safetyReport = new ParseObject("SafetyReport");
    ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
    ParseUser = ParseUser.getCurrentUser();
    safetyReport.put("location", point); 
    safetyReport.put("user", user);
    safetyReport.put("description", d);
    safetyReport.put("support", 0);

    safetyReport.saveInBackground();
  }

  //function for finding reports near the user
  //takes in user location (based on lat & long) and how many reports they want to see 
  public void findNearestReports(int howManyReports, int latitude, int longitude) {
    //parameters should be pulled from user's phone information
    ParseGeoPoint userLocation = new ParseGeoPoint(latitude, longitude);
    ParseQuery<ParseObject> query = ParseQuery.getQuery("SafetyReport");
    query.whereNear("location", userLocation);
    query.setLimit(howManyReports);
    query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> nearReports, ParseException e) {
            if (e == null) {
                //query success 
                //nearReports is of report objects ordered by distance (nearest to farthest from userLocation)
                for (int i=0; i<nearReports.length; i++) {
                    Log.d("Location: "+nearReport[i].get("location"), "Description: "+nearReport[i].getString("description", "Support: "+nearReport[i].getInt("support")));
                }
            }
            else {
                Log.d("Error: "+e.getMessage());
            }
        }
    });
}


    //function for whenever user "agrees" with a report, and clicks button
    //will add 1 to the report's current support count
    public void supportReport(int objId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SafetyReport");
        query.getInBackground(objId, new GetCallback<ParseObject>() {
            public void done(ParseObject report, ParseException e) {
                if (e == null) {
                    report.put("support", report.getInt("support")+1);
                }
                else {
                    Log.d("Error: "+e.getMessage());
                }
            }
        });
    }


    //function to display the maps, I think??
    function displayMap() {

        mv = (MapView) findViewById(R.id.mapview);

        mv.setUserLocationEnabled(true);


        //adding dummy marker
        //can later fill in with info pulled from Parse 
        Marker m = new Marker(mv, "title", "description", new LatLng(100, 100));
        mv.addMarker(m);

    }
}
  
