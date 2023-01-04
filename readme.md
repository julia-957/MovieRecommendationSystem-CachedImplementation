# Movie Recommendation Example
This is a relatively simple example of a recommendation system for the MRS.

## Caching data
It caches the entire database (of flat files) into a cache in the data access layer, consisting of HashMaps of Users and Movies and lastly it
connects these by object references to all the ratings in a two way reference.

It then works solely on the object structure to do its magic.

## Object structure
The Movie has a list of ratings for that movie. The rating holds one Movie. The user has a list of ratings for that user, the rating references 
a single user.

## Solutions
There are 4 different data presentations

### 1. All the already rated movies for a specific user are shown in order of avarage rating for the movie
 - loops through the users rated movies 
 - calculates averages for the movies 
 - sorts using a Java Stream Comparator.

### 2. All the unrated movies for a specific user are shown in order of avarage rating for the movie
 - loops through all movies and discards the ones that is already in the users rating list 
 - calculates averages for the movies 
 - sorts using a Java Stream Comparator.
 
### 3. Compare users and find most similar users
 - For a specific user loops through all users in the system
  - For each movie that both rated, calculate the absolute difference between ratings
  - Calculate the average absolute difference and normalize to a double between 0 and 1
  - Create a list af all other users with their calculated difference to the user
  - Sort by average difference
  - Convert nomrmalized value to percent for presentation
  
### 4. Find unseen movies within the top most similar users 
 - Find top 10 similar users for a specific user
 - For each of these save all unique movies to a list
 - Calculate the average rating per movie based on all the similar users ratings
 - Sort by similarity average
 

See project here:
https://github.com/jeppeml/MovieRecommendationSystem
