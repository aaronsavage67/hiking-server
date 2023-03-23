from locust import HttpUser, task
import random
import string

def get_random_string(length):
    letters = string.ascii_lowercase
    result_str = ''.join(random.choice(letters) for i in range(length))
    return result_str

class TripEndpoints(HttpUser):

    @task
    def trip_endpoints(self):
        random_string = get_random_string(25)

        self.client.get("/getAllTrips")
        self.client.post("/addUserToTrip?username=" + random_string, json =
        {
            "id": 73,
            "mountainId": 28,
            "mountainName": "Mayar",
            "date": "11/9/2018"
        })

        self.client.post("/removeUserFromTrip?username=" + random_string, json =
        {
            "id": 73,
            "mountainId": 28,
            "mountainName": "Mayar",
            "date": "11/9/2018"
        })
        self.client.get("/getTripsByMountainName?mountainName=Dreish")
        self.client.get("/getTripsByDate?date=11/9/2018")
