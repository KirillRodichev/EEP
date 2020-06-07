# NetCracker Java EE Project
### Idea
The idea of this app was to provide potential and existing gyms customers with different types of information:

- General gym info (logo, address, phone, website e.t.c.);
- List of equipment, where each contains info about the target muscle groups, how it should be used and a picture;

This information can be modified by gym administrator (when going through the registration process you can choose your status: user/admin).

### What it looks like

![](https://i.ibb.co/LJ4jzfJ/reg-log.jpg)
> First page allows you to enter existing account or create a new one.

![](https://i.ibb.co/Ybt6Hv2/admin-1.jpg)
> If you continue as an admin, you'll be redirected to your cabinet, where you can modify any information about your gym.

![](https://i.ibb.co/v45Pz0d/admin-2.jpg)
> To navigate to equipment page you should click this giant yellow button.

![](https://i.ibb.co/QD5QvmF/eq-1.jpg)
> Here you can also modify equipment info. Any changes will be updated asynchronously, thanks to js fetch.

![](https://i.ibb.co/jRKDhk6/eq-2.jpg)
> Equipment can be filtered by body groups as well.

![](https://i.ibb.co/FwZJdtn/eq-xml.jpg)
> You can even download an XML file with gym's equipment, modify it and then upload it back. Information from the xml will rewrite existing data in a database.