Mobile Workshop part 2
===

Shenkar -- Amir Uval


### Lesson 1

Fragments

[slides](http://goo.gl/zhNjfp)


#### Homework 1
Create a new github project for the course.

- Create a new project in Android Studio
- Add your own README.md
- VCS menu: enable version control
- Right click on the project --> git --> add
- Click on the vcs up button, do first commit
- VCS menu: import into version control -->   Share project on GitHub

Create a layout with two fragments.
Add a textField on one fragment, and when changed, display the text on the other fragment.
Use [EventBus](http://greenrobot.org/eventbus/) for the communication between the fragments.

- push the changes to GitHab


### Lesson 2

Firebase

Install the [Lesson's apk](stuff/lesson2.apk)

Tip: [making](https://github.com/auval/svg2png) this app logo from [svg](stuff/shenkar2_logo.svg).


[slides](http://goo.gl/S8CsmK)

#### Homework 2

##### - Code

Integrate Firebase to your homework project.
Write a "lobby" fragment with a "sign in" button+capability, and alternating TextView or a RecyclerView
- Implement [signing in](https://github.com/firebase/FirebaseUI-Android/tree/master/auth)
- Unsigned user will see the string:
   - "There are %s signed-in users and %s anonymous guests" in the TextView
  (replace %s with the proper number).
- signed in users will see the list and a "sign out" button.
   - display a list of names of signed-in users.
   - display "and %s anonymous guests", where %s is the number of unsigned users.
- signing out will refresh the above UI accordingly

##### - Experiment

Viable apps evolve over time, and you will need to modify a live database.
It's very important for your database to support different versions of your application at the same time. 
Check what happens when a class mapped to the json tree changes:

 - field added
 - field removed
 - field renamed
 - field changes type


1. Report the results of this experiment
1. How do you think is best to maintain backward compatibility?


#### Brainstorm

Future features of your app will include:
- make a higher level "group", lobby will be inside it
- users will have roles within group: admin/member/guest
- group can belong to one or more super group (group of groups, or tag)
  (to allow users to find groups they are not members of)
    - a group can be marked "hidden" 
- allow a logged in user to create groups
- allow users to join and leave existing groups
- show a list groups, and how many users are logged in in each group
- if the last admin leaves the group, 
- if last user is leaving a group, delete the group from the server
- user will be able to see all groups in his institution (school)

What is the best data structure that fits the requirements?
What is the logic flow for handling user registration and role? (admin/member/guest) 


### Lesson 3

Firebase (cont)
App Widget

Install the [Lesson's apk](stuff/lesson3.apk)


[slides](https://goo.gl/eV4BC1)

#### Homework 3

Extend on the firebase+widget sample app.
- add widget configuration option to select a topic (TextField is acceptable, list of existing topics is better)
- each widget will register as listener only for its topic, and display its current value.

### Lesson 4

- App Widgets
- Services

[slides](https://goo.gl/5UKF8B)

### Lesson 5

- Data Binding
- Dependency Injection

[slides](https://goo.gl/hC2fAH)



### Lesson 6

- Dependency Injection (Dagger 2)
- Unit Testing

[slides](https://goo.gl/wHkKVv)

### Lesson 7

- continues lesson 6 topics
- Mockito

[slides](https://goo.gl/2hPsv8)


Exercise:
Find and replace RegEx
<pre>
change this:
0xff443399 
to 
0xff_44_33_99 
</pre>

### Lesson 8

- Retrofit [Sample project](https://github.com/auval/WeatherRest)
- Sheets API
  -- need to authorise the app to use the API [here](https://console.developers.google.com/apis/api/sheets.googleapis.com/overview).

[slides](https://goo.gl/iiwtW2)



### Lesson 9

- Memory leaks

[slides](https://goo.gl/y9TUwf)

### Lesson 10

- The new "Android Architecture Components"

[slides](https://goo.gl/3pXSbc)




