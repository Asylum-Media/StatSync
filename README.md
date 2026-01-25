# StatSync

**StatSync** is a lightweight Paper plugin designed to give Minecraft server staff a **clear, contextual snapshot of a player** at the moment they need it — without invasive tracking, performance overhead, or brittle placeholder hacks.

It is built specifically to integrate with **CommandPanels**, **scoreboard-based economies**, and **cross-play servers (Geyser/Floodgate)**.

> _Context, not judgement. Signals, not surveillance._

---

## ✨ What StatSync Does

StatSync provides a **single command** that captures a player’s current state and writes it into **CommandPanels data**, ready to be displayed in staff GUIs.

It snapshots:

- Player platform (Java / Bedrock device via Floodgate)
- Economy balance from a configurable scoreboard objective
- Session delta (Beans/$ gained or lost since join / enable)
- Additional context hooks (AFK state, CoreProtect, jobs, etc. — expandable)

All data is:
- **Staff-scoped**
- **Read-only in panels**
- **Ephemeral by design**

---

## 🎯 Design Goals

StatSync is intentionally opinionated:

- ✔ Scoreboards are the **single source of truth**
- ✔ No event spam or invasive listeners
- ✔ No assumptions about *why* something happened
- ✔ No replacing existing moderation tools
- ✔ Clean separation between logic and presentation

It exists to answer:
> “What’s going on *right now* with this player?”

—not to police, accuse, or log endlessly.

---

## 🔌 Integrations

StatSync plays nicely with:

- **CommandPanels** (data storage + GUI rendering)
- **Floodgate** (Bedrock platform detection)
- **Scoreboard-based economies** (Beans, $, etc.)

Optional / future-facing integrations:
- CoreProtect (recent activity signals)
- AFK plugins
- Jobs / Quests plugins

---

## ⚙️ Configuration

### `config.yml`

```yaml
economy:
  beans-objective: Beans
