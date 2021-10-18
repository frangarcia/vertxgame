# Game

The game was written to show the feature of Eclipse Vert.x in a presentation at http://vertx.frangarcia.net. The game is basically a way of showing up the capabilities of the Vert.x event bus and how to use it. 

You can start the game locally with the next commands 

```
docker build -t vertxgame .
docker run -d --name myvertxgame -it -p 8282:8080 vertxgame
```

The game has 2 pages:

- http://localhost:8282/game/index.html, where users will be able to vote for a team
- http://localhost:8282/game/panel.html, where you can check how many votes each team has

Then, there are other rest api endpoints to check the votes per team and user.

- http://localhost:8282/api/votes, to check the total number of votes per team
- http://localhost:8282/api/standings, to check the total number of votes each user has given to each team
