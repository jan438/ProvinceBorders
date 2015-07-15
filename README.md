# ProvinceBorders
Input dataset prepared following: http://www.geon.nl/nieuws/nieuws/item/3115-van-gis-naar-google

ogr2ogr -f KML -s_srs "+proj=sterea +lat_0=52.15616055555555 +lon_0=5.38763888888889 +k=0.999908 +x_0=155000 +y_0=463000 +ellps=bessel +units=m +towgs84=565.2369,50.0087,465.658,-0.406857330322398,0.350732676542563,-1.8703473836068,4.0812 +no_defs no_defs" -t_srs EPSG:3857 /home/jan/Downloads/geoserver-GetFeature.kml /home/jan/Downloads/geoserver-GetFeature.xml

File /home/jan/Downloads/geoserver-GetFeature.xml retrieved from
http://geodata.nationaalgeoregister.nl/bestuurlijkegrenzen/wfs?service=WFS&version=2.0.0&request=GetFeature&typename=provincies

Copyright © 2015 Jan Boon janboon438@gmail.com. Code released under the The MIT License 

