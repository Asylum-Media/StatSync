# **StatSync**

**StatSync** is a lightweight Paper plugin designed to give Minecraft server staff a **clear, contextual snapshot of a player** at the moment they need it — without invasive tracking, performance overhead, or brittle placeholder hacks.

It is built specifically to integrate with **CommandPanels**, **scoreboard-based economies**, and **cross-play servers (Geyser/Floodgate)**.

> _Context, not judgement. Signals, not surveillance._

---

## ✨ **What StatSync Does**

StatSync provides a **single command** that captures a player’s current state and writes it into **CommandPanels data**, ready to be displayed in staff GUIs.

**It snapshots:**
- Player platform (Java / Bedrock device via Floodgate)
- Economy balance from a configurable scoreboard objective
- Session delta (Beans/$ gained or lost since join / enable)
- Additional context hooks (AFK state, CoreProtect, jobs, etc. — expandable)

**All data is:**
- **Staff-scoped**
- **Read-only in panels**
- **Ephemeral by design**

---

## 🎯 Design Goals

**StatSync is intentionally opinionated:**

- ✔ Scoreboards are the **single source of truth**
- ✔ No event spam or invasive listeners
- ✔ No assumptions about *why* something happened
- ✔ No replacing existing moderation tools
- ✔ Clean separation between logic and presentation

It exists to answer:
> “What’s going on *right now* with this player?”

—_not to police, accuse, or log endlessly._

---

## 🔌 **Integrations**

**StatSync plays nicely with:
**
- **CommandPanels** (data storage + GUI rendering)
- **Floodgate** (Bedrock platform detection)
- **Scoreboard-based economies** (Beans, $, etc.)

_Optional / future-facing integrations:_
- CoreProtect (recent activity signals)
- AFK plugins
- Jobs / Quests plugins

---

## ⚙️ **Configuration**

### `config.yml`

```yaml
economy:
  beans-objective: Beans
```
## 🧪 **Usage**
Command
`/statsync context <player>`

**What it does**

- Resolves the target player
- Snapshots live data (platform, economy, session delta)
- Writes values into CommandPanels data for the staff member running the command

**Example keys written:**

- ccm_target_platform
- ccm_target_beans_current
- ccm_target_beans_session


These are intended to be consumed by CommandPanels, not chat.

## 🧱 **CommandPanels Workflow (Recommended)**

**_StatSync is designed to be used like this:_**

Staff enters a player name (dialog panel)

**CommandPanels clears old context:**

`/pa data clear <staff>`

**StatSync snapshots new context:**

`/statsync context <target>`

**A read-only info panel opens and displays the data**

_CommandPanels data is treated as a scratchpad, not a database._

## 🧠 **Why Session Deltas?**

StatSync tracks session-based changes, not historical totals.

This allows staff to see:

- Whether currency changed during this session
- Rough scale of activity
- Whether something looks worth reviewing

_It deliberately avoids:_

- Attribution guessing
- Accusatory language
- Persistent tracking

This follows a law-of-averages moderation philosophy.

## 🛡️ **Permissions**

StatSync does not manage permissions itself.
Restrict usage via:

- Your permissions plugin (LuckPerms, etc.)
- CommandPanels access control

## 🧩 **Philosophy**

_StatSync is not:_

- An anti-cheat
- A logging system
- A replacement for CoreProtect or moderation tools

_It is:_

- A context engine
- A glue layer
- A staff quality-of-life tool
Designed for adult communities, cross-play servers, and staff who value clarity over noise.

## 📦 **Building**

_StatSync is built with Gradle and targets:_

- Java 21
- Paper 1.21+
- Floodgate API (optional, soft-depend)

Standard Gradle build:

`./gradlew build`

## 🧡 **Credits**

Developed by Asylum-Media

Built for real-world server administration, not theorycraft.

_Inspired by:_

- Practical moderation needs
- Scoreboard-authoritative economies
- Clean separation of concerns
- _Julie Tapia_ 🧡

## 📄 **Licence**

MIT — do what you like, _just don’t pretend you wrote it_ 😉

## 🚧 **Status**

- StatSync is actively evolving alongside its host servers.

_**Expect:**_

- Incremental features
- Strong backwards compatibility
- No rushed bloat

PRs and discussion welcome.
