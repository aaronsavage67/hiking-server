from locust import HttpUser, task
import random

class MunroBagEndpoints(HttpUser):

    @task
    def munro_bag_endpoints(self):
        random_mountain_id = random.randrange(1, 100000000)

        self.client.get("/getMunrosBaggedByUsername?username=LocustTests")
        response = self.client.post("/addMunroToBag", json =
        {
            "username": "LocustTests",
            "mountainId": random_mountain_id,
            "mountainName": "Ben Nevis",
            "date": "12/6/2022",
            "rating": "4"
        })

        json_response = response.json()
        munro_bag_id = json_response[0]['id']

        self.client.post("/removeMunroFromBag", json =
        {
            "id": munro_bag_id,
            "username": "LocustTests",
            "mountainId": random_mountain_id,
            "mountainName": "Ben Nevis",
            "date": "12/6/2022",
            "rating": "4"
        })
