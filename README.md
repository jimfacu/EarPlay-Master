# EarPlay

EarPlay es un ejercicio mobile simple en Android para poder escuchar musica.

## Descripción

Mediante Retrofit hacemos peticiones a la API de Deezer, guardando los resultados para ser mostrados en una interfaz diseñada en base 
a elementos de Material Design.Transformando objetos JSON a objetos JAVA mediante la librería de Google GSON.La informacion se muestra en 
distintos RecyclerViews , tambien se cuenta con la posibilidad de poder crear nuestras propias Playlist y poder darle likes a canciones que 
se agregan automaticamente a un album de Canciones Favoritas . Toda esta informacion se guarda en la cuenta de cada persona que es creada antes
de poder utilizar todas estas funcionalidades . La cuenta es creada y guardada en Firebase con la informacion antes mencionada y con la 
posibilidad de poder mandar notificaciones a la app.

## Librerías

WEB SERVICES

  * **FirebaseDatabase**  'com.google.firebase:firebase-database:16.0.4'
  * **FirebaseAuth**      'com.google.firebase:firebase-auth:16.0.5'
  * **FirebaseMessaging** 'com.google.firebase:firebase-messaging:17.3.4'
  * **Retrofit**          'com.squareup.retrofit2:retrofit:2.5.0'
  * **API** **REST** **DEEZER** 
   
MATERIAL DESIGN

  * **RecyclerView**   'com.android.support:recyclerview-v7:29.1.1'
  * **Material**     'com.google.android.material:material:1.0.0'
  
   
EXTRA COMPLEMENTS
   
  * **Glide**            'com.github.bumptech.glide:glide:4.7.1'
  * **ButterKnife**      'com.jakewharton:butterknife:10.0.0'
  * **CircleImageView**  'de.hdodenhof:circleimageview:3.1.0'
 
# MATERIAL ELEMENTS

    - RecyclerView
    - BottomNavigationView
    - CircleImageView
    - Spinner
    
