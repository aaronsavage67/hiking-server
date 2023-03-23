from locust import HttpUser, task
import random
import string

def get_random_string(length):
    letters = string.ascii_lowercase
    result_str = ''.join(random.choice(letters) for i in range(length))
    return result_str

class UserEndpoints(HttpUser):

    @task
    def user_endpoints(self):
        self.client.get("/getUserByUsername?username=LocustTests")
        self.client.get("/getUserByPassword?password=1000:9d56125af9277ad77880506fd3ff1ea5:83a083f3030691ce46000d859ef28d92ab2681f71e1b265e72ae9be57db7a85657dffc42627dc6abbe6ffdc088bad21681f3a515892cdcdbfc67d27c87370b76")
        self.client.post("/createUser?username=" + get_random_string(25) + "&password=secPassword1&email=aaron.g.savage@icloud.com")
        self.client.post("/validateUserCode?username=HashTest9&code=777371")
        self.client.post("/resendCode?username=LocustTests")
        self.client.post("/generateNewCode?username=Screenshot10")
        self.client.post("/isUsernameActivated?username=LocustTests")
        self.client.post("/resetPassword?username=BlackBoxTest&newPassword=newPass1&code=001610")
        self.client.post("/validateLogin?username=LocustTests&password=secPassword1")
