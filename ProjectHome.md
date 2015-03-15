# What is a ranking? #
_Rankings_ are a relationship between a set of objects such that one object is better, or ranked higher than another. For instance, a list of one's favorite ice cream cones is a ranking. See: http://en.wikipedia.org/wiki/Ranking

# What are you doing with rankings? #
In this project, we are _clustering_ rankings. This means that we are finding rankings that are similar enough, and grouping them together in a way that makes the most sense. We compute what are called _cluster centers_ from a set partial rankings. See: http://en.wikipedia.org/wiki/Cluster_sampling

# How is this even useful? #
That is a great question! Imagine you are a burger restaurant, and you want to put three burgers on the menu that best fits what everyone wants. You go to R&D and they conduct a survey asking 2000 people for their favorite toppings to be put on a burger in order. What do you do with this data? You cluster it! You can find three "cluster centers" that best fit the data, which could represent three burgers at your restaurant.

# How does this differ from cluster points in a 2d plane? #
Unlike a 2d plane, distances between rankings cannot always be represented in 2d or even 3d spaaace. Because of this, it is hard to visualize how each ranking relates to one another. See: http://www.machinelearning.org/proceedings/icml2007/papers/341.pdf for more information on the math behind our code.

# Does anyone else do this? #
Yes, there is an official R package called "RankCluster" that does essentially the same thing as this project. It is available at: http://cran.r-project.org/web/packages/Rankcluster/index.html