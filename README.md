# LumberPlot

<center>
<img src="https://github.com/ame94/LumberPlot/blob/master/logo.png"><br>
Automated plots for all your lumber needs!<br>
</center>

Tired of players not replanting after harvest? Sick of players putting blocks
that don't belong in the farm or it simply just getting griefed?
 
**LumberPlot** solves these issues and more.
 
### Requirements:

LumberPlot's only requirement is WorldEdit.

### Features:
 
1. Automatic sapling replacement when the log at the base of the tree is harvested.
1. Works with all (currently six) tree varieties.
1. Players may only break logs, leaves & vines and nothing else.
1. Players cannot place any blocks yet bonemeal usage still permitted.
1. Drops from Leaf block decay removed to prevent lag. Saplings, apples etc can still be obtained if leaf blocks are broken individually.
1. Quickly define any number of plots with WorldEdit's selection wand.
1. Multiple world support (Multiverse or anything similar).
1. Allows quick adjustments inside plots with permission node or while in creative.
 
### Permissions:

Node setup is simple and straight forward:
 
| **Node** | **Description** |
| --- | --- |
| `lumberplot.admin` | Full perms to create, delete and modify inside of plots |
| `lumberplot.modify` | Allows block changes within existing plots |

### Command reference:
| **Command** | **Description** |
| --- | --- |
| `/lumberplot define <plotname>` | Defines a new plot for the world you're in |
| `/lumberplot delete <plotname>` | Removes a plot. |
| `/lumberplot list` | Gets a listing of all plots. |
| `/lumberplot reload` | Reloads the config file. |
| `/lumberplot help` | Gets help information. |

### Usage:
 
To define a new plot:
1. Make a selection with the WorldEdit `//wand` tool, `//pos1` & `//pos2`, etc.
1. Type `/lumberplot define <plotname>` where plotname is your new plot.
1. **Done!** Nothing further is needed and your tree farm is now protected.
