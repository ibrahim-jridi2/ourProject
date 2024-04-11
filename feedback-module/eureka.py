import random
import string
import os
import py_eureka_client.eureka_client as eureka_client


N = 32
res = ''.join(random.choices(string.ascii_lowercase + string.digits, k=N))


server_port = 8761


def eureka_init():
    if os.environ.get("RUN_MAIN"):
        eureka_client.init(eureka_server="http://eurekaserver:8761/eureka",
                        app_name="feedback_module",
                        instance_port=server_port,
                        )
        print("Eureka client is running")


def stop_eureka():
    if os.environ.get("RUN_MAIN"):
        eureka_client.stop()
        print("Stopping Eureka client")
        
# eureka_init()