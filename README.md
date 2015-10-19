# SmartHouseAPItest

<b>2015-10-07</b>
<br>minor changes in structure, Data class sclaed and migrated to replace envData.class

<b>2015-10-04</b>
<br>redesign of exposed URI's: 

/House <br>
/House/{id}<br>
/House/{id}/User<br>
/House/{id}/User/{id}<br>
/House/{id}/Room<br>
/House/{id}/Room/{id}<br>
/House/{id}/Room/{id}/Device<br>
/House/{id}/Room/{id}/Device/{id}<br>
/House/{id}/Room/{id}/Environment<br>

<b>2015-10-05</b>
<br>Abstracted baseclasses from the object-model

<b>2015-10-05</b>
<br>ConcurrentHashMap typemigrated to Collection because of marchalling problems. 
