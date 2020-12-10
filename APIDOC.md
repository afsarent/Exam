1.Store Image

Resource Path : /file<br>
Method : POST<br>
Request param: "file" = Multipart , String id = 112<br>
Response: "File is created."<br>

2.List of Image on specific id<br>

Resource Path : /file/directory<br>
Method : GET<br>
Request param: String id = "122"<br>
Response: 	[<br>
			    "1607107018186.png",<br>
			    "1607107029819.png",<br>
			    "1607109203928.png"<br>
			]

3.Download image for specific directory<br>

Resource Path : /file/download<br>
Method : GET<br>
Request param: String filename = 1607109203928.png , String id = 122<br>