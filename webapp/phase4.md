# Phase 4: Database Design and Population

### **Authors**: Ari Conati & Grant Lee

------

**Variables for our Dataset**:

* Performance
* Date
* Venue (name, location)
* Conductor
* Piece (name, composer)
* Soloists ([(soloist, instrument)...])

------

**Table Name:** Performances  

**Command:** 
```SQL
CREATE TABLE performances (
    id SERIAL,
    date_id integer,
    venue_id integer,
    conductor_id integer,
    piece_id integer,
    soloists_id integer
);

```
**Reasoning:**   
This is the table responsible for keeping track of each performance. This is the main table that keeps track of all results that will ever be presented to the end user.

------

**Table Name:** Dates

**Command:**    
```SQL
CREATE TABLE dates (
    id SERIAL,
    date DATE
);
```
**Reasoning:**
Dates should have its own separate table because there can be multiple dates that correspond to a performance. For example, there can be multiple dates for the nutcracker suite.
 
------

**Table Name:** Venues 

**Command:** 
```SQL
CREATE TABLE venues (
    id SERIAL,
    name text,
    location text
);
```
**Reasoning:**
Because the venue will vary from performance to performance, there will be a separate table for each given venue. The venue will also include a location which corresponds to the location that the venue will be located.
 
------

**Table Name:** Locations 

**Command:** 
```SQL
CREATE TABLE locations (
    id SERIAL,
    name text
);
```
**Reasoning:**
This is the table for locations that correspond to a venue.
 
------

**Table Name:** Conductors 

**Command:** 
```SQL
CREATE TABLE conductors (
    conductor_id SERIAL,
    name text
);
```
**Reasoning:**
This is the table for each individual conductor which has a one to many relationship to performance.

------

**Table Name:** Pieces

**Command:** 
```SQL
CREATE TABLE pieces (
    piece_id SERIAL,
    title text,
    composer text
);
```
**Reasoning:**
This is the table for each individual piece performed, this will have a composer. 

------

**Table Name:** Composers

**Command:** 
```SQL
CREATE TABLE composers (
    composer_id SERIAL,
    title text
);
```
**Reasoning:**
This is the table for composers.

------


**Table Name:** Soloists

**Command:** 
```SQL
CREATE TABLE soloists (
    soloist_id SERIAL,
    name text,
    instrument text
);
```
**Reasoning:**
This is the table for each individual piece performed, this will have an instrument that gets played

------

**Table Name:** Instruments

**Command:** 
```SQL
CREATE TABLE instruments (
    intrument_id SERIAL,
    name text
);
```
**Reasoning:**
This is the table for instruments.

------

**Table Name:** Link_Performance_Date

**Command:** 
```SQL
CREATE TABLE performances_dates (
    performance_id integer,
    date_id integer
);
```
**Reasoning:**
This is the table that links the performance and dates.

------

**Table Name:** Link_Performance_Venue

**Command:** 
```SQL
CREATE TABLE performances_venues (
    performance_id integer,
    venue_id integer
);
```
**Reasoning:**
This table links performance and venue.

------

**Table Name:** Link_Venue_Location

**Command:** 
```SQL
CREATE TABLE venues_locations (
    venue_id integer,
    location_id integer
);
```
**Reasoning:**
This links venues to their corresponding location.

------

**Table Name:** Link_Performance_Conductors

**Command:** 
```SQL
CREATE TABLE performances_conductors (
    performance_id integer,
    conductor_id integer
);

```
**Reasoning:**
This links the performance and the conductor 

------

**Table Name:** Link_Performance_Piece

**Command:** 
```SQL
CREATE TABLE performances_pieces (
    piece_id SERIAL,
    performance_id integer
);
```
**Reasoning:**
This links the performance and the piece 

------

**Table Name:** Link_Soloist_Instrument 

**Command:** 
```SQL
CREATE TABLE soloists_instruments (
    soloist_id integer,
    intrument_id integer
);
```
**Reasoning:**
This links the soloist and their instrument.

------

**Table Name:** Link_Performance_Soloist

**Command:** 
```SQL
CREATE TABLE performances_soloists (
    performance_id integer,
    soloist_id integer
);
```
**Reasoning:**
This links the performance to soloist. 

------





