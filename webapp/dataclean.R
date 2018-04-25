library(dplyr)

data <- read.csv("assignments-grantIee/webapp/ny_phil.csv")
data2 <- select(data, -c(eventType, id, Time, interval,  soloistRoles, season, programID, orchestra, movement))
duplicate <- duplicated(data2)
head(duplicate, n = 30)
data3 <- data2[!duplicate, ]
data3$workTitle[1152] == ""
noWorkTitle <- data3$workTitle == ""
data4 <- data3[!noWorkTitle, ]
data4$soloistName[data4$soloistName == ""] <- "No Soloist"
data4$soloistInstrument[data4$soloistInstrument == ""] <- "Other"
noSoloistInstrument <- data4$soloistInstrument == ""
data5 <- data4[!noSoloistInstrument, ]
data5$soloistName[91606]
summary(data5)
NASoloistInstrument <- is.na(data5$soloistInstrument)
NASoloistInstrument
data7 <- data6[!NASoloistInstrument, ] 

summary(data7)


write.csv(data7, file="assignments-grantIee/webapp/data2.csv")

