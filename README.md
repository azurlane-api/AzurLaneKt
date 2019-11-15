[![](https://jitpack.io/v/azurlane-api/AzurLaneKt.svg)](https://jitpack.io/#azurlane-api/AzurLaneKt)
[![](https://jitci.com/gh/KurozeroPB/AzurLaneKt/svg)](https://jitci.com/gh/KurozeroPB/AzurLaneKt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cd784d3ff0e2472eb429bcad34fbdd66)](https://www.codacy.com/manual/KurozeroPB/AzurLaneKt?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=KurozeroPB/AzurLaneKt&amp;utm_campaign=Badge_Grade)

# AzurLaneKt
Wrapper for the unofficial azur lane json api in Kotlin

## Add dependency

<details><summary>Gradle</summary>

```kotlin
repositories {
    maven(url = "https://jitpack.io")
}
```

```kotlin
dependencies {
    implementation("com.github.azurlane-api:AzurLaneKt:Tag")
}
```
</details>

<details><summary>Maven</summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.azurlane-api</groupId>
    <artifactId>AzurLaneKt</artifactId>
    <version>Tag</version>
</dependency>
```
</details>

## Example
```kotlin
package com.example

import com.github.azurlane_api.api.AzurLane
import com.github.azurlane_api.api.Order

object Example {

    @JvmStatic
    fun main(args: Array<String>) {
        val azurlane = AzurLane("custom_ua/v0.1.0")

        val ships = azurlane.getShips(Order.RARITY, "Super Rare")
        ships.forEach { ship -> println("[${ship.id}]: (${ship.name})") }
    }

}
```

## Support
![discord](https://discordapp.com/api/v6/guilds/240059867744698368/widget.png?style=banner2)
