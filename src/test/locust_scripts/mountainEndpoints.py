from locust import HttpUser, task

class MountainEndpoints(HttpUser):

    @task
    def mountain_endpoints(self):
        self.client.get("/getImage?munro=Dreish")
        self.client.get("/getAllMountains")
        self.client.get("/getMountainByName?name=Dreish")
        self.client.get("/getMountainById?id=27")
        self.client.get("/getMountainsByRegion?region=Fort William")
