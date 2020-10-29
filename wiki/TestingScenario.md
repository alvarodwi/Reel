**UNIT TEST**

- NetworkResultTest
  - Memastikan data yang dimasukkan di dalam NetworkResult.Success sesuai
  - Memastikan exception yang terjadi di dalam NetworkResult.Error memiliki message yang sama

- ApiServiceTest
  - Memastikan ketika api ditembak, data yang didapatkan sesuai dengan yang ada di resource
  - Testing dilakukan untuk :
    - Bentuk List dari Movie dan TvShow
    - Bentuk Single dari Movie dan TvShow



**ANDROID TEST**

- MovieFragmentTest
  - Memastikan ketika MovieListFragment dibuka, recycler view bisa discroll ke index terakhir
  - Memastikan ketika item recycler view di klik, halaman detail terbuka

- TvShowFragmentTest
  - Memastikan ketika TvShowListFragment dibuka, recycler view bisa discroll ke index terakhir
  - Memastikan ketika item recycler view di klik, halaman detail terbuka