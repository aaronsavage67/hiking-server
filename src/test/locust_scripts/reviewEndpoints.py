from locust import HttpUser, task

class ReviewEndpoints(HttpUser):

    @task
    def review_endpoints(self):
        self.client.get("/getAllReviews")
        self.client.post("/createReview", json =
        {
            "username": "gerard88",
            "reviewDate": "27/5/2000",
            "mountainId": 27,
            "mountainName": "Dreish",
            "walkDate": "19/11/1980",
            "rating": "4",
            "comment": "it was fun"
        })
        self.client.get("/getReviewsByMountainName?name=Mayar")