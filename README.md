# Movie Display Application Overview:

Description:
Our app will allow the user to search a movie they like and it will return similar movies. Upon clicking on a similar movie the user will be taken to an info activity for that movie where they can learn more, watch the trailer, or open the IMDB page. The app will let user customize settings to limit search results to certain ratings, etc. We will use the search movies query to return a list of movies during the users initial search. When they select a movie we will pass that movie’s ID to the get similar movies query which will return a list of similar movies. When they pick a similar movie they want to learn more about, we will pass the movie’s ID to the get movie details query which will return useful data such as release date, popularity, summary, and more on a page dedicated to that movie. 

API Links:
https://developers.themoviedb.org/3/movies/get-similar-movies
https://developers.themoviedb.org/3/movies/get-movie-details
https://developers.themoviedb.org/3/search/search-movies

Original Prototype/Mock:
https://www.figma.com/file/iF1spVQk8ZXEv06TcSk1tg/CS492-Final-Prototype?node-id=0%3A1

