SocailApp Service
-
App where user can register, log in, log out, leave a post, see his feed (a list of posts by people he follows), see another user's feed (without his subscriptions), comment on a post, leave or remove a like on a post (his own or someone else's), and follow another user.


Queries
-
- Create a user
- Edit a user
- Delete a user
- Create a post
- Editing a post
- Deleting a post
- Subscription to a user (the subscriber's feed will receive posts from the user to whom he/she subscribed)
- Unsubscribe from a user
- Commenting on a post
- Get a user's feed (including likes and comments)
- Get another user's feed
- Get post comments
- Likes can be left and deleted

Installation and launch instructions:
1. Clone the repository - git clone https://github.com/MaksymMedvedchuk/SocialApp.git
2. Make clean and build project - ./gradlew clean build
3. Run docker-compose file - docker-compose up
4. Create DB via Mongo Express(admin, pass) - http://localhost:8081/
5. Run SocialApp file

SWAGGER UI
-
http://localhost:8080/swagger-ui/index.html#/

MONGO EXPRESS
-
http://localhost:8081
