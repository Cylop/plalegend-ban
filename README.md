# Playlegend Ban Plugin
Simple Ban plugin that uses ORM with Mysql and DI via Google Guice

[![Latest release](https://github.com/Cylop/playlegend-ban/releases/tag/v0.0.2)
![Love](https://img.shields.io/badge/Love-pink?style=flat-square&logo=data:image/svg%2bxml;base64,PHN2ZyByb2xlPSJpbWciIHZpZXdCb3g9IjAgMCAyNCAyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48dGl0bGU+R2l0SHViIFNwb25zb3JzIGljb248L3RpdGxlPjxwYXRoIGQ9Ik0xNy42MjUgMS40OTljLTIuMzIgMC00LjM1NCAxLjIwMy01LjYyNSAzLjAzLTEuMjcxLTEuODI3LTMuMzA1LTMuMDMtNS42MjUtMy4wM0MzLjEyOSAxLjQ5OSAwIDQuMjUzIDAgOC4yNDljMCA0LjI3NSAzLjA2OCA3Ljg0NyA1LjgyOCAxMC4yMjdhMzMuMTQgMzMuMTQgMCAwIDAgNS42MTYgMy44NzZsLjAyOC4wMTcuMDA4LjAwMy0uMDAxLjAwM2MuMTYzLjA4NS4zNDIuMTI2LjUyMS4xMjUuMTc5LjAwMS4zNTgtLjA0MS41MjEtLjEyNWwtLjAwMS0uMDAzLjAwOC0uMDAzLjAyOC0uMDE3YTMzLjE0IDMzLjE0IDAgMCAwIDUuNjE2LTMuODc2QzIwLjkzMiAxNi4wOTYgMjQgMTIuNTI0IDI0IDguMjQ5YzAtMy45OTYtMy4xMjktNi43NS02LjM3NS02Ljc1em0tLjkxOSAxNS4yNzVhMzAuNzY2IDMwLjc2NiAwIDAgMS00LjcwMyAzLjMxNmwtLjAwNC0uMDAyLS4wMDQuMDAyYTMwLjk1NSAzMC45NTUgMCAwIDEtNC43MDMtMy4zMTZjLTIuNjc3LTIuMzA3LTUuMDQ3LTUuMjk4LTUuMDQ3LTguNTIzIDAtMi43NTQgMi4xMjEtNC41IDQuMTI1LTQuNSAyLjA2IDAgMy45MTQgMS40NzkgNC41NDQgMy42ODQuMTQzLjQ5NS41OTYuNzk3IDEuMDg2Ljc5Ni40OS4wMDEuOTQzLS4zMDIgMS4wODUtLjc5Ni42My0yLjIwNSAyLjQ4NC0zLjY4NCA0LjU0NC0zLjY4NCAyLjAwNCAwIDQuMTI1IDEuNzQ2IDQuMTI1IDQuNSAwIDMuMjI1LTIuMzcgNi4yMTYtNS4wNDggOC41MjN6Ii8+PC9zdmc+)


## 1. Usage
If you want to use this plugin, keep in mind that it's still in development and bugs may occurr. I haven't found any so far but If you do, please add an Issue.

Go to releases and download the latest .jar. The version v0.0.1-alpha supports Spigot 1.18.
After the first run you will find a directory in the plugins folder with the name "PlaylegendBans". Go into that directory, open config.yml and set the mysql credentials. 
Without that set, the plugin won't start.

## 2. Commands
The plugin provides 2 commands. One for ban and one for unban.

### Ban
```
Usage: /ban <player> [duration] [message or reason]

Alias: b
Permission: playlegend.ban
```

Bans the specifed player for a specific amount of time (duration) and with a message. 

#### Default Values

| Argument      | Default value | Configurable  |
| ------------- |:-------------:| -----:|
| Duration      | 999y          |       |
| Message       | Unbekannter Grund      |   X   | 

For the format of the duration see section 3 (Duration Format)

### Unban
```
Usage: /unban <player>

Alias: ub
Permission: playlegend.unban
```

## 3. Duration Format
The duration for a ban can be provided in a simple format. 

For example like this: `3M7d` will be translated to 3 months and 7 days those will be added to the current date

In action: `/ban Notch 3M7d Cheating`

### Allowed units
| Unit      | Timeunit |
| --------- |:--------:|
| y         | Year     |
| M         | Month    |
| w         | Week     |
| d         | Day      |
| h         | Hour     |
| s         | Second   |

Duration can be changed as long as you wish. `1y7M7w1s10h3d2y9h4M27s` is also working.

