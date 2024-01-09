import json

def program():
    json_str = '{"Id": 12345}'

    data = json.loads(json_str)

    print(data['Id'])
    

    