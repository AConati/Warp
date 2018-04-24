library(dplyr)

data <- read.csv("assignments-grantIee/webapp/ny_phil.csv")
data2 <- select(data, -c(eventType, id, Time, interval,  soloistRoles, season, programID, orchestra))
duplicate <- duplicated(data2)
head(duplicate, n = 30)
data3 <- data2[!duplicate, ]
data3$workTitle[1152] == ""
noWorkTitle <- data3$workTitle == ""
data4 <- data3[!noWorkTitle, ]
data4$soloistName[data4$soloistName == ""] <- "No Soloist"
data4$soloistInstrument[data4$soloistInstrument == ""] <- "Other"


write.csv(data4, file="assignments-grantIee/webapp/data2.csv")
